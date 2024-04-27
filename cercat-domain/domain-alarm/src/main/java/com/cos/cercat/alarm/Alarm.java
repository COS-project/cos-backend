package com.cos.cercat.alarm;

import com.cos.cercat.user.User;
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
