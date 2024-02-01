package com.cos.cercat.alarm.dto;

import com.cos.cercat.alarm.domain.Alarm;
import com.cos.cercat.alarm.domain.AlarmType;
import com.cos.cercat.user.domain.User;
import lombok.AllArgsConstructor;

public record AlarmEvent(
        User receiveUser,
        AlarmArg alarmArg,
        AlarmType alarmType
) {

    public static AlarmEvent of(User receiveUser, AlarmArg alarmArg, AlarmType alarmType) {
        return new AlarmEvent(
                receiveUser,
                alarmArg,
                alarmType
        );
    }

    public Alarm toEntity() {
        return new Alarm(
                receiveUser,
                alarmArg.fromUser(),
                alarmArg.targetId(),
                alarmType
        );
    }
}
