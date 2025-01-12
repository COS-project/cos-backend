package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AlarmManager {

    private final AlarmRepository alarmRepository;

    public Alarm save(Alarm alarm) {
        return alarmRepository.save(alarm);
    }

    public List<Alarm> read(User user) {
        return alarmRepository.findUnreadAlarms(user);
    }

    public void markAsRead(List<Long> alarmId) {
        alarmRepository.markAsRead(alarmId);
    }

    public int countUnread(User user) {
        return alarmRepository.countUnreadAlarms(user);
    }
}
