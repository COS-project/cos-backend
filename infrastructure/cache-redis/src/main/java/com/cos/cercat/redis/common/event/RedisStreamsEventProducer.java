package com.cos.cercat.redis.common.event;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.common.event.EventPublisher;
import com.cos.cercat.domain.common.event.outbox.OutboxEventManager;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisStreamsEventProducer implements EventPublisher {

    private final StringRedisTemplate redisTemplate;
    private final OutboxEventManager outboxEventManager;

    private final static ObjectMapper redisEventMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                    ObjectMapper.DefaultTyping.EVERYTHING,
                    JsonTypeInfo.As.PROPERTY);

    @Override
    public void publish(Event event) {
        String streamKey = getKey(event);

        try {
            redisTemplate.opsForStream().add(
                    StreamRecords.newRecord()
                            .ofMap(
                                    Map.of(
                                            "payload", redisEventMapper.writeValueAsString(event)
                                    )
                            ).withStreamKey(streamKey)
            );

            log.debug("이벤트 발행 완료 [{}]: {}", streamKey, event);
            outboxEventManager.append(outboxEventManager.read(event.resolveId()).markAsProcessed());

        } catch (Exception e) {
            log.error("이벤트 발행 실패 [{}]: {}", streamKey, event, e);
            outboxEventManager.append(outboxEventManager.read(event.resolveId()).retry(e.getMessage()));
        }
    }

    private String getKey(Event event) {
        return "event:" + event.resolveType();
    }
}
