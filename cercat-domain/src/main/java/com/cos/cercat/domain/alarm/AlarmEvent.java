package com.cos.cercat.domain.alarm;


import com.cos.cercat.domain.user.User;

public record AlarmEvent(
        User recieveUser,
        AlarmArg alarmArg,
        AlarmType alarmType
) {

    public static AlarmEvent of(User user, AlarmArg alarmArg, AlarmType alarmType) {
        return new AlarmEvent(
                user,
                alarmArg,
                alarmType
        );
    }
}
