package com.cos.cercat.alarm.app.kafka.consumer;

import com.cos.cercat.alarm.app.usecase.AlarmCreateUseCase;
import com.cos.cercat.alarm.dto.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmCreateUseCase alarmCreateServiceUseCase;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consume(AlarmEvent event, Acknowledgment ack) {
        log.info("kafka consume event {}", event);
        alarmCreateServiceUseCase.createAndSendAlarm(event);
        ack.acknowledge();
    }
}
