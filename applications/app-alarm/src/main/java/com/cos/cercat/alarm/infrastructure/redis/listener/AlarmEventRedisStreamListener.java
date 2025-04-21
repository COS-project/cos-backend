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
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.PendingMessage;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmEventRedisStreamListener implements
        StreamListener<String, MapRecord<String, String, String>> {

    private final StringRedisTemplate redisTemplate;
    private final InboxEventManager inboxEventManager;
    private final AlarmEventDeadLetterRedisStreamProducer dlqProducer;
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

    private StreamOperations<String, String, String> streamOps;

    @PostConstruct
    public void init() {
        streamOps = redisTemplate.opsForStream();
    }

    @Override
    public void onMessage(MapRecord<String, String, String> record) {
        try {
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

    @Scheduled(fixedRate = 60_000)
    public void retryPendingMessages() {
        log.info("Pending 메시지 재처리 시작");
        for (String type : eventTypes) {
            String key = "event:" + type;
            try {
                var pending = streamOps
                        .pending(key, group, Range.unbounded(), 100);

                // 배달 횟수를 포함한 맵 생성
                Map<RecordId, Long> deadLetterInfo = pending.stream()
                        .filter(pm -> pm.getTotalDeliveryCount() > maxRetryCount)
                        .collect(Collectors.toMap(
                                PendingMessage::getId,
                                PendingMessage::getTotalDeliveryCount,
                                (count1, count2) -> count2
                        ));

                List<RecordId> claimTargets = pending.stream()
                        .filter(pm -> pm.getTotalDeliveryCount() <= maxRetryCount)
                        .map(PendingMessage::getId)
                        .toList();


                // DLQ로 이동할 메시지가 있으면 배치 처리 (배달 횟수 정보 전달)
                if (!deadLetterInfo.isEmpty()) {
                    int movedCount = dlqProducer.publish(key, deadLetterInfo, group);

                    if (movedCount > 0) {
                        log.debug("DLQ로 {} 개의 메시지 이동 완료 - stream={}", movedCount, key);
                    }
                }

                if (claimTargets.isEmpty()) {
                    continue;
                }

                // 재시도 대상
                var claimed = streamOps
                        .claim(key, group, consumerName,
                                Duration.ofMinutes(5), claimTargets.toArray(new RecordId[0]));

                for (var msg : claimed) {
                    onMessage(msg);
                }
            } catch (Exception e) {
                log.error("Pending 메시지 처리 중 오류 발생 - stream={}", key, e);
            }
        }
    }

    private void ack(MapRecord<String, String, String> record) {
        if (streamOps == null) {
            streamOps = redisTemplate.opsForStream();
        }
        try {
            streamOps.acknowledge(Objects.requireNonNull(record.getStream()), group, record.getId());
        } catch (Exception e) {
            log.error("메시지 승인(ACK) 실패 - stream={}, id={}", record.getStream(), record.getId(), e);
        }
    }
}
