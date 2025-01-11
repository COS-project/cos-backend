package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Alarm {

    private final Long alarmId;
    private final User receiver;
    private final AlarmType alarmType;
    private final LocalDateTime alarmTime;

    public Alarm(Long alarmId, User receiver, AlarmType alarmType, LocalDateTime alarmTime) {
        this.alarmId = alarmId;
        this.receiver = receiver;
        this.alarmType = alarmType;
        this.alarmTime = alarmTime;
    }

}
