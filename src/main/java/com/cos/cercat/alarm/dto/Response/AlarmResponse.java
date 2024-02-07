package com.cos.cercat.alarm.dto.Response;

import com.cos.cercat.alarm.domain.Alarm;
import com.cos.cercat.alarm.domain.AlarmType;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.dto.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AlarmResponse(
        Long alarmId,
        Long targetPostId,
        AlarmType alarmType,
        String alarmText
) {
    public static AlarmResponse from(Alarm entity) {

        AlarmType alarmType = entity.getAlarmType();

        return new AlarmResponse(
                entity.getId(),
                entity.getPostId(),
                alarmType,
                makeAlarmText(alarmType, UserDTO.fromEntity(entity.getFromUser()))
        );
    }

    private static String makeAlarmText(AlarmType alarmType, UserDTO fromUser) {
        return switch (alarmType) {
            case NEW_LIKE_ON_POST -> "회원님의 게시글에 " + fromUser.getNickname() + "님이 좋아요를 눌렀습니다.";
            case NEW_COMMENT_ON_POST -> "회원님의 게시글에 " + fromUser.getNickname() + "님이 댓글을 달았습니다.";
            case NEW_LIKE_ON_COMMENT -> "회원님의 댓글에 " + fromUser.getNickname() + "님이 좋아요를 눌렀습니다.";
            case NEW_COMMENT_ON_COMMENT -> "회원님의 댓글에 " + fromUser.getNickname() + "님이 대댓글을 달았습니다";
            default -> null;
        };
    }
}
