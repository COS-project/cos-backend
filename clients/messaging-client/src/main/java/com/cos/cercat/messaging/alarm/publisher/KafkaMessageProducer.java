package com.cos.cercat.messaging.alarm.publisher;

import com.cos.cercat.domain.common.Event;
import com.cos.cercat.domain.common.EventPublisher;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageProducer implements EventPublisher {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    public void publish(Event event) {
        CompletableFuture<SendResult<String, Event>> sentResult =
                kafkaTemplate.send(event.getType(), event.getKey(), event);

        sentResult.whenComplete(
                (result, ex) -> {
                    RecordMetadata metadata = result.getRecordMetadata();
                    if (ex != null) {
                        log.error("Failed to send message to topic: {}", event.getType(), ex);
                    } else {
                        log.info(
                                "Sent message to topic: {}, partition: {}, offset: {}, timestamp: {}",
                                metadata.topic(),
                                metadata.partition(),
                                metadata.offset(),
                                metadata.timestamp()
                        );
                    }
                }
        );
    }

}
