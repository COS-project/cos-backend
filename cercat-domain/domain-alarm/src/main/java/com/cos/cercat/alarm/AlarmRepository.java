package com.cos.cercat.alarm;


import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;

import java.util.List;

public interface AlarmRepository {

    List<Alarm> findUnreadAlarms(User user);

    int countUnreadAlarms(User user);

    void markAsRead(User user);

    void save(AlarmEvent alarmEvent);
}
