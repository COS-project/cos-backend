package com.cos.cercat.kafka.producer;

import com.cos.cercat.domain.alarm.AlarmEvent;
import com.cos.cercat.domain.alarm.AlarmSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmProducer implements AlarmSender {

    @Value("${spring.kafka.topic.alarm}")
    private String topic;
    private final KafkaTemplate<Long, AlarmEvent> kafkaTemplate;

    public void send(AlarmEvent event) {
        kafkaTemplate.send(topic, event.recieveUser().id(), event);
        log.info("Kafka alarm send complete");
    }

}
