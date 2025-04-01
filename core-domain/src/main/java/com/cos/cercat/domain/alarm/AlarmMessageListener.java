package com.cos.cercat.domain.alarm;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmMessageListener {

    private final AlarmManager alarmManager;
    private final List<NotificationChannel> notificationChannels;


    @Transactional
    public void notifyAlarm(Alarm alarm) {
        try {
            alarmManager.save(alarm);
            for (NotificationChannel channel : notificationChannels) {
                channel.send(alarm);
                log.debug("Successfully sent alarm to channel: {}", channel.getClass().getSimpleName());
            }
        } catch (Exception e) {
            log.error("Failed to process alarm: {}", alarm.getAlarmType(), e);
            throw e;
        }
    }
}
