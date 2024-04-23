package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AlarmManager {

    private final AlarmRepository alarmRepository;

    public List<Alarm> read(TargetUser targetUser) {
            return alarmRepository.findUnreadAlarms(targetUser);

    }

    @Transactional
    public void markAsRead(TargetUser targetUser) {
        alarmRepository.markAsRead(targetUser);
    }

    public int countUnread(TargetUser targetUser) {
        return alarmRepository.countUnreadAlarms(targetUser);
    }

    public void append(AlarmEvent alarmEvent) {
        alarmRepository.save(alarmEvent);
    }


}
