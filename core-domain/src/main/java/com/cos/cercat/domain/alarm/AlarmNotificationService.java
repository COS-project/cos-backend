package com.cos.cercat.domain.alarm;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmNotificationService {

    private final AlarmManager alarmManager;
    private final List<NotificationChannel> notificationChannels;

    @Async
    public void notify(Alarm alarm) {
        Alarm savedAlarm = alarmManager.save(alarm);
        notificationChannels.forEach(channel -> channel.send(savedAlarm));
    }

}
