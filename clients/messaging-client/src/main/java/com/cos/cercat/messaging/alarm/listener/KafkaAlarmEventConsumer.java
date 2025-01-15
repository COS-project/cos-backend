package com.cos.cercat.messaging.alarm.listener;

import com.cos.cercat.domain.alarm.Alarm;
import com.cos.cercat.domain.alarm.AlarmNotifier;
import com.cos.cercat.domain.common.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Profile("alarm")
@RequiredArgsConstructor
@Slf4j
public class KafkaAlarmEventConsumer {

    private final AlarmNotifier alarmNotifier;

    @KafkaListener(topics = "#{'${kafka.topic.alarm-events}'.split(',')}", groupId = "${kafka.consumer-group.alarm}")
    public void consume(ConsumerRecord<String, Event> record, Acknowledgment ack) {
        Event alarmEvent = record.value();
        log.info("Consumed eventKey: {}, eventType: {}", alarmEvent.getKey(), alarmEvent.getType());
        alarmNotifier.notify(Alarm.from(alarmEvent));
        ack.acknowledge();
    }
}
