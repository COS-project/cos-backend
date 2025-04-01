package com.cos.cercat.alarm.messaging.listener;

import com.cos.cercat.domain.alarm.Alarm;
import com.cos.cercat.domain.alarm.AlarmMessageListener;
import com.cos.cercat.domain.common.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmEventKafkaListener {

    private final AlarmMessageListener alarmMessageListener;

    @KafkaListener(topics = "#{'${kafka.topic.alarm-events}'.split(',')}",
            groupId = "${kafka.consumer-group.alarm}")
    public void consume(
            ConsumerRecord<String, Event> record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Event alarmEvent = record.value();
        log.debug("이벤트 수신 - 토픽: {}, 키: {}, 타입: {}",
                topic, alarmEvent.resolveKey(), alarmEvent.resolveType());

        Alarm alarm = Alarm.from(alarmEvent);
        alarmMessageListener.notifyAlarm(alarm);

        ack.acknowledge();
        log.debug("이벤트 처리 완료 - 토픽: {}", topic);
    }
}
