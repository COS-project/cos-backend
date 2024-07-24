package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.User;

public interface AlarmPublisher {
    void publish(User receiver, User sender, Long targetId, AlarmType alarmType);
}
