package com.cos.cercat.domain.alarm;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmNotifier {

    private final AlarmManager alarmManager;
    private final List<NotificationChannel> notificationChannels;

    @Transactional
    public void notifyAlarm(Alarm alarm) {
        alarmManager.save(alarm);
        for (NotificationChannel channel : notificationChannels) {
            channel.send(alarm);
            log.debug("Successfully sent alarm to channel: {}", channel.getClass().getSimpleName());
        }
    }
}
