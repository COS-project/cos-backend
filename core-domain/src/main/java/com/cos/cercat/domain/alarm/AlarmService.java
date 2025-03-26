package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmManager alarmManager;
    private final UserReader userReader;

    public List<Alarm> readAlarms(UserId userId) {
        User user = userReader.read(userId);
        return alarmManager.read(user);
    }

    public void markAsRead(List<Long> alarmIds) {
        alarmManager.markAsRead(alarmIds);
    }

    public int countUnreadAlarms(UserId userId) {
        User user = userReader.read(userId);
        return alarmManager.countUnread(user);
    }
}
