package com.cos.cercat.apis.alarm.response;

import com.cos.cercat.domain.alarm.Alarm;
import com.cos.cercat.domain.alarm.AlarmType;
import com.cos.cercat.domain.alarm.BoardAlarm;
import com.cos.cercat.domain.alarm.ExamAlarm;
import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AlarmResponse(
        Long alarmId,
        Long targetPostId,
        AlarmType alarmType,
        String alarmText,
        LocalDateTime alarmTime
) {

    public static AlarmResponse from(Alarm alarm) {
        return new AlarmResponse(
                alarm.getAlarmId(),
                alarm instanceof BoardAlarm boardAlarm ? boardAlarm.getPostId() : null,
                alarm.getAlarmType(),
                makeAlarmText(alarm),
                alarm.getAlarmTime()
        );
    }

    private static String makeAlarmText(Alarm alarm) {
        if (alarm instanceof BoardAlarm boardAlarm) {
            return makeBoardAlarmText(boardAlarm.getAlarmType(), boardAlarm.getFromUser());
        }
        return makeExamAlarmText(alarm.getAlarmType(), ((ExamAlarm) alarm).getCertificate());
    }


    private static String makeBoardAlarmText(AlarmType alarmType, User user) {
        return switch (alarmType) {
            case NEW_LIKE_ON_POST -> "회원님의 게시글에 " + user.getNickname() + "님이 좋아요를 눌렀습니다!";
            case NEW_COMMENT_ON_POST -> "회원님의 게시글에 " + user.getNickname() + "님이 댓글을 달았습니다!";
            case NEW_LIKE_ON_COMMENT -> "회원님의 댓글에 " + user.getNickname() + "님이 좋아요를 눌렀습니다!";
            case REPLY_ON_COMMENT -> "회원님의 댓글에 " + user.getNickname() + "님이 대댓글을 달았습니다!";
            default -> null;
        };
    }

    private static String makeExamAlarmText(AlarmType alarmType, CertificateExam certificateExam) {
        return switch (alarmType) {
            case BEFORE_APPLICATION ->
                    "회원님의 관심자격증 " + certificateExam.certificate().certificateName()
                            + " 접수 하루 남았어요!";
            case DEADLINE -> "회원님의 관심자격증 " + certificateExam.certificate().certificateName()
                    + " 마감 하루 남았어요!";
            case START_APPLICATION ->
                    "회원님의 관심자격증 " + certificateExam.certificate().certificateName() + " 접수를 시작했어요!";
            default -> null;
        };
    }

}
