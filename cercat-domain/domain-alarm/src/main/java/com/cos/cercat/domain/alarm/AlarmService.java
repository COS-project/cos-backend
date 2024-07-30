package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.TargetUser;
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
}
