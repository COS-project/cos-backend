package com.cos.cercat.kafka.consumer;

import com.cos.cercat.alarm.AlarmEvent;
import com.cos.cercat.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consume(AlarmEvent event, Acknowledgment ack) {
        log.info("AlarmConsumer consume event : {}", event);
        alarmService.sendAlarm(event);
        ack.acknowledge();
    }
}
