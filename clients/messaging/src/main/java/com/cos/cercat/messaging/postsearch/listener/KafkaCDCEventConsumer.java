package com.cos.cercat.messaging.postsearch.listener;

import com.cos.cercat.messaging.postsearch.debezium.DebeziumEvent;
import com.cos.cercat.messaging.postsearch.EventHandlerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaCDCEventConsumer {

    private final EventHandlerFactory eventHandlerFactory;

    @KafkaListener(topics = {"${spring.kafka.topic.debezium_post}", "${spring.kafka.topic.debezium_comment}"}, groupId = "${kafka.consumer-group.debezium}")
    public void handleEvents(ConsumerRecord<String, DebeziumEvent> record,
                             Acknowledgment acknowledgment) {

        if (isReadOperation(record)) {
            acknowledgment.acknowledge();
            return;
        }
        eventHandlerFactory.getHandler(record.topic()).process(record.value());
        log.info("{} 이벤트를 {} 토픽에 처리 요청", record.value().getPayload().getOperation(), record.topic());
        acknowledgment.acknowledge();
    }

    private static boolean isReadOperation(ConsumerRecord<String, DebeziumEvent> record) {
        return record.value().getPayload().getOperation()
                == DebeziumEvent.DebeziumEventPayloadOperation.READ;
    }

}