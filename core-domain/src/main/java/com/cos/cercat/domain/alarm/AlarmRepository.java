package com.cos.cercat.domain.alarm;


import com.cos.cercat.domain.user.User;

import java.util.List;

public interface AlarmRepository {

    Alarm save(Alarm alarm);

    List<Alarm> findUnreadAlarms(User user);

    int countUnreadAlarms(User user);

    void markAsRead(List<Long> alarmIds);
}
