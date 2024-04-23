package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.User;
import lombok.Getter;

@Getter
public class Alarm {
    private final User receiver;
    private final AlarmType alarmType;

    public Alarm(User receiver, AlarmType alarmType) {
        this.receiver = receiver;
        this.alarmType = alarmType;
    }

}
