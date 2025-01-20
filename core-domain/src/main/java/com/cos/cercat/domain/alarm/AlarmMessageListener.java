package com.cos.cercat.domain.alarm;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmMessageListener {

    private final AlarmManager alarmManager;
    private final List<NotificationChannel> notificationChannels;

    public void notifyAlarm(Alarm alarm) {
        Alarm savedAlarm = alarmManager.save(alarm);
        notificationChannels.forEach(channel -> channel.send(savedAlarm));
    }

}
