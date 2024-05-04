package com.cos.cercat.alarm;

import com.cos.cercat.user.User;

public interface AlarmSender {
    void send(User receiver, User sender, Long targetId, AlarmType alarmType);
}
