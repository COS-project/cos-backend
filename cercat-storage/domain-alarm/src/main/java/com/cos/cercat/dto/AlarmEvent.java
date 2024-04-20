package com.cos.cercat.dto;

import com.cos.cercat.domain.AlarmType;
import com.cos.cercat.domain.BoardAlarm;
import com.cos.cercat.domain.UserEntity;

public record AlarmEvent(
        UserEntity receiveUserEntity,
        AlarmArg alarmArg,
        AlarmType alarmType
) {

    public static AlarmEvent of(UserEntity receiveUserEntity, AlarmArg alarmArg, AlarmType alarmType) {
        return new AlarmEvent(
                receiveUserEntity,
                alarmArg,
                alarmType
        );
    }

    public BoardAlarm toEntity() {
        return new BoardAlarm(
                receiveUserEntity,
                alarmType,
                false,
                alarmArg.fromUserEntity(),
                alarmArg.postId()
        );
    }
}
