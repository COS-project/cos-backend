package com.cos.cercat.alarm.infrastructure.kafka.listener;


import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.common.event.inbox.InboxEvent;
import com.cos.cercat.domain.common.event.inbox.InboxEventManager;
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

    private final InboxEventManager inboxEventManager;

    @KafkaListener(topics = "#{'${kafka.topic.alarm-events}'.split(',')}",
            groupId = "${kafka.consumer-group.alarm}")
    public void consume(
            ConsumerRecord<String, Event> record,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Event alarmEvent = record.value();
        log.debug("이벤트 수신 - 토픽: {}, 키: {}, 타입: {}",
                topic, alarmEvent.resolveKey(), alarmEvent.resolveType());

        if (inboxEventManager.exists(alarmEvent.resolveId())) {
            log.warn("이벤트 중복 처리 - 토픽: {}, 키: {}",
                    topic, alarmEvent.resolveKey());
            ack.acknowledge();
            return;
        }

        inboxEventManager.append(InboxEvent.from(alarmEvent));
        ack.acknowledge();
        log.debug("이벤트 처리 완료 - 토픽: {}", topic);
    }
}
