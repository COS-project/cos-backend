package com.cos.cercat.alarm.dto.Response;

import com.cos.cercat.alarm.domain.Alarm;
import com.cos.cercat.alarm.domain.AlarmType;
import com.cos.cercat.user.dto.response.UserResponse;

public record AlarmResponse(
        Long alarmId,
        UserResponse fromUser,
        Long targetId,
        AlarmType alarmType
) {
    public static AlarmResponse from(Alarm entity) {
        return new AlarmResponse(
                entity.getId(),
                UserResponse.fromEntity(entity.getFromUser()),
                entity.getTargetId(),
                entity.getAlarmType()
        );
    }
}
