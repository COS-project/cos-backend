package com.cos.cercat.alarm.infrastructure.redis.listener;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.common.event.inbox.InboxEvent;
import com.cos.cercat.domain.common.event.inbox.InboxEventManager;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.PendingMessage;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmEventRedisStreamListener {

    private final StringRedisTemplate redisTemplate;
    private final InboxEventManager inboxEventManager;
    private final AlarmEventDeadLetterRedisStreamProducer dlqProducer;
    private StreamOperations<String, String, String> streamOps;

    private final static ObjectMapper redisEventMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                    ObjectMapper.DefaultTyping.EVERYTHING,
                    JsonTypeInfo.As.PROPERTY);


    @Value("#{'${redis.key-prefix.alarm-events}'.split(',')}")
    private List<String> eventTypes;

    @Value("${redis.consumer-group.alarm}")
    private String group;

    @Value("${spring.application.name}")
    private String consumerName;

    @Value("${redis.max-retry-count}")
    private int maxRetryCount;

    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> container;
    private final List<Subscription> subscriptions = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void start() {
        StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainerOptions.builder()
                        .batchSize(100)
                        .pollTimeout(Duration.ofSeconds(2))
                        .build();

        streamOps = redisTemplate.opsForStream();

        container = StreamMessageListenerContainer.create(
                redisTemplate.getConnectionFactory(), options);

        eventTypes.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .forEach(this::subscribeStream);

        container.start();
        log.info("Redis Stream 컨슈머 시작 – group={}, consumer={}", group, consumerName);
    }

    private void subscribeStream(String type) {
        String key = "event:" + type;
        createGroupIfAbsent(key);

        Subscription sub = container.receive(
                Consumer.from(group, consumerName),
                StreamOffset.create(key, ReadOffset.lastConsumed()),
                this::onMessage);

        subscriptions.add(sub);
        log.info("➡️  구독 시작 – key={}", key);
    }

    private void onMessage(MapRecord<String, String, String> record) {
        try {
            // payload 키에서 JSON 문자열 가져오기
            String eventJson = record.getValue().get("payload");
            Event event = redisEventMapper.readValue(eventJson, Event.class);
            String eventId = event.resolveId();

            log.debug("이벤트 수신 – key={}, id={}, type={}",
                    record.getStream(), eventId, event.resolveType());

            if (inboxEventManager.exists(eventId)) {
                log.warn("중복 이벤트 – id={}", eventId);
                ack(record);
                return;
            }

            inboxEventManager.append(InboxEvent.from(event));
            ack(record);
            log.debug("이벤트 처리 완료 – id={}", eventId);
        } catch (Exception ex) {
            log.error("이벤트 처리 실패", ex);
        }
    }

    @Scheduled(fixedDelay = 60_000)
    public void retryPendingMessages() {
        for (String type : eventTypes) {
            String key = "event:" + type;

            // 최대 100개 조회
            var pending = streamOps
                    .pending(key, group, Range.unbounded(), 100);

            List<RecordId> claimTargets = pending.stream()
                    .filter(pendingMessage -> pendingMessage.getTotalDeliveryCount() < 5)
                    .map(PendingMessage::getId)
                    .toList();

            List<RecordId> deadLetterIds = pending.stream()
                    .filter(pendingMessage -> pendingMessage.getTotalDeliveryCount() > 5)
                    .map(PendingMessage::getId)
                    .toList();

            // DLQ로 이동할 메시지가 있으면 배치 처리
            if (!deadLetterIds.isEmpty()) {
                int movedCount = dlqProducer.moveToDlq(key, deadLetterIds, group,
                        "최대 재시도 횟수(" + maxRetryCount + ") 초과");

                if (movedCount > 0) {
                    log.info("DLQ로 {} 개의 메시지 이동 완료 - stream={}", movedCount, key);
                }
            }

            if (claimTargets.isEmpty()) {
                continue;
            }

            // minIdleTime 을 0 으로 주면 이미 idle 필터링했으니 바로 클레임
            var claimed = streamOps
                    .claim(key, group, consumerName,
                            Duration.ofMinutes(10), claimTargets.toArray(new RecordId[0]));

            for (var msg : claimed) {
                onMessage(msg);
            }
        }
    }


    private void ack(MapRecord<String, String, String> record) {
        streamOps.acknowledge(
                record.getStream(), group, record.getId());
    }

    private void createGroupIfAbsent(String key) {
        try {
            streamOps.createGroup(key, group);
            log.info("컨슈머 그룹 생성 – key={}, group={}", key, group);
        } catch (Exception ignore) { /* 이미 존재하면 무시 */ }
    }

    @PreDestroy
    public void stop() {
        subscriptions.forEach(Subscription::cancel);
        if (container != null) container.stop();
        log.info("Redis Stream 컨슈머 종료");
    }
}
