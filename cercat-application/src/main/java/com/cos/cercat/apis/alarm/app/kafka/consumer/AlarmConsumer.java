package com.cos.cercat.apis.alarm.app.kafka.consumer;

import com.cos.cercat.apis.alarm.app.usecase.AlarmCreateUseCase;
import com.cos.cercat.domain.alarm.dto.AlarmEvent;
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
