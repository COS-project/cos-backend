package com.cos.cercat.redis;


import com.cos.cercat.domain.alarm.AlarmEvent;
import com.cos.cercat.domain.alarm.AlarmPublisher;
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
    public void publish(AlarmEvent alarm) {
        log.info("RedisMessageManager publish: {}", alarm);
        redisTemplate.convertAndSend(CHANNEL_PREFIX, alarm);
    }
}
