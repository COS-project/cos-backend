package com.cos.cercat.alarm;

import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmManager alarmManager;
    private final SseProcessor sseProcessor;

    public List<Alarm> readAlarms(TargetUser targetUser) {
        List<Alarm> alarmList = alarmManager.read(targetUser);
        alarmManager.markAsRead(targetUser);
        return alarmList;
    }

    public int countUnreadAlarms(TargetUser targetUser) {
        return alarmManager.countUnread(targetUser);
    }

    public void sendAlarm(AlarmEvent alarmEvent) {
        alarmManager.append(alarmEvent);
        sseProcessor.sendEvent(alarmEvent);
    }

}
