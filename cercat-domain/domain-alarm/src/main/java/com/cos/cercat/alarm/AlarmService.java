package com.cos.cercat.alarm;

import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmManager alarmManager;
    private final UserReader userReader;
    private final AlarmSubscribeManager alarmSubscribeManager;

    public List<Alarm> readAlarms(TargetUser targetUser) {
        User user = userReader.read(targetUser);
        List<Alarm> alarmList = alarmManager.read(user);
        alarmManager.markAsRead(user);
        return alarmList;
    }

    public int countUnreadAlarms(TargetUser targetUser) {
        User user = userReader.read(targetUser);
        return alarmManager.countUnread(user);
    }

    public void subscribe(Long userId) {
        alarmSubscribeManager.subscribe(userId);
    }

    @Async
    @EventListener
    public void unsubscribe(SseClosedEvent event) {
        alarmSubscribeManager.unsubscribe(event.userId());
    }

}
