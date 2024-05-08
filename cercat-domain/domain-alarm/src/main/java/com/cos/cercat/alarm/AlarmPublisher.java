package com.cos.cercat.alarm;

import com.cos.cercat.user.User;

public interface AlarmPublisher {
    void publish(User receiver, User sender, Long targetId, AlarmType alarmType);
}
