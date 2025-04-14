package com.cos.cercat.alarm.infrastructure.sse;

import com.cos.cercat.domain.alarm.Alarm;
import com.cos.cercat.domain.alarm.NotificationChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SSEChannel implements NotificationChannel {

    private final RedisAlarmBroadcaster alarmBroadcaster;

    @Override
    public void send(Alarm alarm) {
        alarmBroadcaster.broadcast(alarm);
    }
}
