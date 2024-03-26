package com.cos.cercat.apis.alarm.app.kafka.producer;

import com.cos.cercat.dto.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmProducer {

    @Value("${spring.kafka.topic.alarm}")
    private String topic;

    private final KafkaTemplate<Long, AlarmEvent> kafkaTemplate;

    public void send(AlarmEvent event) {
        kafkaTemplate.send(topic, event.receiveUser().getId(), event);
        log.info("Kafka alarm send complete");
    }

}
