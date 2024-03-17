package com.cos.cercat.post.app.search;

import com.cos.cercat.post.app.search.handler.EventHandlerFactory;
import com.cos.cercat.post.app.search.handler.PostEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventDispatcher {

    private final EventHandlerFactory eventHandlerFactory;

    @KafkaListener(topics = {"${spring.kafka.topic.debezium}"})
    public void handleEvents(List<ConsumerRecord<String, DebeziumEvent>> records,
                             Acknowledgment acknowledgment) {
        log.info("Request to process {} records", records.size());

        List<ConsumerRecord<String, DebeziumEvent>> sortedRecords = records.stream()
                .sorted(Comparator.comparing(r -> r.value().getPayload().getDate()))
                .toList();

        sortedRecords.forEach(record -> {
            log.info("Request to handle {} event in the topic {}", record.value().getPayload().getOperation(), record.topic());
            eventHandlerFactory.getHandler(record.topic()).process(record.value());
        });
        acknowledgment.acknowledge();
    }

}
