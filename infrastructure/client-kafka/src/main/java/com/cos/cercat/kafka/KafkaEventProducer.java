package com.cos.cercat.kafka;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.common.event.EventPublisher;
import com.cos.cercat.domain.common.event.outbox.OutboxEventManager;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventProducer implements EventPublisher {

    private final KafkaTemplate<String, Event> kafkaTemplate;
    private final OutboxEventManager outboxEventManager;

    @Override
    public void publish(Event event) {
        log.debug("Publishing event to Kafka: {}", event);

        CompletableFuture<SendResult<String, Event>> sendResult =
                kafkaTemplate.send(event.resolveType(), event.resolveKey(), event);

        sendResult.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send event to Kafka: {}", ex.getMessage(), ex);
                outboxEventManager.append(outboxEventManager.read(event.resolveId()).retry(ex.getMessage()));
            } else {
                log.debug("Event sent to Kafka successfully: {}", result.getProducerRecord());
                outboxEventManager.append(outboxEventManager.read(event.resolveId()).markAsProcessed());
            }
        });
    }
}
