package com.cos.cercat.alarm;


import com.cos.cercat.user.TargetUser;

import java.util.List;

public interface AlarmRepository {

    List<Alarm> findUnreadAlarms(TargetUser user);

    int countUnreadAlarms(TargetUser targetUser);

    void markAsRead(TargetUser targetUser);

    void save(AlarmEvent alarmEvent);
}
