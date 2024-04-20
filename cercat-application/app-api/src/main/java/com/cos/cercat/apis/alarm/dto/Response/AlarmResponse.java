package com.cos.cercat.apis.alarm.dto.Response;

import com.cos.cercat.domain.*;
import com.cos.cercat.dto.UserDTO;
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

        return switch (alarmType) {
            case BEFORE_APPLICATION, DEADLINE, START_APPLICATION -> from((ExamAlarm) entity);
            default -> from((BoardAlarm) entity);
        };
    }

    private static AlarmResponse from(BoardAlarm boardAlarm) {
        return new AlarmResponse(
                boardAlarm.getId(),
                boardAlarm.getPostId(),
                boardAlarm.getAlarmType(),
                makeBoardAlarmText(boardAlarm.getAlarmType(), UserDTO.fromEntity(boardAlarm.getFromUserEntity()))
        );
    }

    private static AlarmResponse from(ExamAlarm examAlarm) {
        return new AlarmResponse(
                examAlarm.getId(),
                null,
                examAlarm.getAlarmType(),
                makeExamAlarmText(examAlarm.getAlarmType(), examAlarm.getCertificateExamEntity())
        );
    }

    private static String makeBoardAlarmText(AlarmType alarmType, UserDTO fromUser) {
        return switch (alarmType) {
            case NEW_LIKE_ON_POST -> "회원님의 게시글에 " + fromUser.getNickname() + "님이 좋아요를 눌렀습니다!";
            case NEW_COMMENT_ON_POST -> "회원님의 게시글에 " + fromUser.getNickname() + "님이 댓글을 달았습니다!";
            case NEW_LIKE_ON_COMMENT -> "회원님의 댓글에 " + fromUser.getNickname() + "님이 좋아요를 눌렀습니다!";
            case NEW_COMMENT_ON_COMMENT -> "회원님의 댓글에 " + fromUser.getNickname() + "님이 대댓글을 달았습니다!";
            default -> null;
        };
    }

    private static String makeExamAlarmText(AlarmType alarmType, CertificateExamEntity certificateExamEntity) {
        return switch (alarmType) {
            case BEFORE_APPLICATION -> "회원님의 관심자격증 " + certificateExamEntity.getCertificateEntity().getCertificateName() + " 접수 하루 남았어요!";
            case DEADLINE -> "회원님의 관심자격증 " + certificateExamEntity.getCertificateEntity().getCertificateName() + " 마감 하루 남았어요!";
            case START_APPLICATION -> "회원님의 관심자격증 " + certificateExamEntity.getCertificateEntity().getCertificateName() + " 접수를 시작했어요!";
            default -> null;
        };
    }

}
