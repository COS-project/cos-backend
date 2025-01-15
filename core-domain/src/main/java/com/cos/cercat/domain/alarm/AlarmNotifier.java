package com.cos.cercat.domain.alarm;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmNotifier {

    private final AlarmManager alarmManager;
    private final List<NotificationChannel> notificationChannels;

    @Async
    public void notify(Alarm alarm) {
        Alarm savedAlarm = alarmManager.save(alarm);
        notificationChannels.forEach(channel -> channel.send(savedAlarm));
    }

}
