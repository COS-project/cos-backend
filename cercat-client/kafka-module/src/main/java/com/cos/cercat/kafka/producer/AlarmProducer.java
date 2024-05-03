package com.cos.cercat.kafka.producer;

import com.cos.cercat.alarm.AlarmArg;
import com.cos.cercat.alarm.AlarmEvent;
import com.cos.cercat.alarm.AlarmSender;
import com.cos.cercat.alarm.AlarmType;
import com.cos.cercat.post.Post;
import com.cos.cercat.user.User;
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

    public void send(User receiver, User sender, Long targetId, AlarmType alarmType) {
        AlarmEvent alarm = createAlarm(receiver, sender, targetId, alarmType);
        kafkaTemplate.send(topic, alarm.recieveUser().getId(), alarm);
        log.info("Kafka alarm send complete");
    }

    private AlarmEvent createAlarm(User receiver, User sender, Long targetId, AlarmType alarmType) {
        return AlarmEvent.of(receiver, AlarmArg.of(sender, targetId), alarmType);
    }


}
