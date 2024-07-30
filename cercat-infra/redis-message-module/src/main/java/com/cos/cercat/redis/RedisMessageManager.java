package com.cos.cercat.redis;


import com.cos.cercat.domain.alarm.AlarmArg;
import com.cos.cercat.domain.alarm.AlarmEvent;
import com.cos.cercat.domain.alarm.AlarmPublisher;
import com.cos.cercat.domain.alarm.AlarmType;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageManager implements AlarmPublisher {

    static String CHANNEL_PREFIX = "ALARM_CHANNEL";

    private final RedisTemplate<String, AlarmEvent> redisTemplate;

    @Override
    public void publish(User receiver, User sender, Long targetId, AlarmType alarmType) {
        AlarmEvent alarm = createAlarm(receiver, sender, targetId, alarmType);
        log.info("RedisMessageManager publish: {}", alarm);
        redisTemplate.convertAndSend(CHANNEL_PREFIX, alarm);
    }

    private AlarmEvent createAlarm(User receiver, User sender, Long targetId, AlarmType alarmType) {
        return AlarmEvent.of(receiver, AlarmArg.of(sender, targetId), alarmType);
    }
}
