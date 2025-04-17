package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.certificate.event.external.InterestCertificateExamScheduleEvent;
import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.like.event.external.LikeCreatedEvent;
import com.cos.cercat.domain.post.event.external.CommentCreatedEvent;

import java.time.LocalDateTime;

public class AlarmFactory {

    public static Alarm create(Event event) {
        return switch (event.resolveType()) {
            case "like-created" -> {
                LikeCreatedEvent like = (LikeCreatedEvent) event;
                yield LikeAlarm.builder()
                        .originId(like.targetId())
                        .likeTargetType(like.targetType())
                        .receiver(like.targetOwner())
                        .likerId(like.likerId())
                        .likerNickname(like.likerNickname())
                        .alarmType(AlarmType.NEW_LIKE_ON_POST)
                        .alarmTime(LocalDateTime.now())
                        .build();
            }

            case "comment-created" -> {
                CommentCreatedEvent comment = (CommentCreatedEvent) event;
                yield Alarm.builder()
                        .originId(comment.targetPostId())
                        .receiver(comment.targetPostOwner())
                        .alarmType(AlarmType.NEW_COMMENT_ON_POST)
                        .alarmTime(LocalDateTime.now())
                        .build();
            }

            case "interest-certificate-exam-schedule" -> {
                InterestCertificateExamScheduleEvent exam = (InterestCertificateExamScheduleEvent) event;
                yield ExamAlarm.from(exam.certificateExam(), exam.user(), exam.scheduleCheckType());
            }

            default -> throw new IllegalStateException("Unexpected value: " + event.resolveType());
        };
    }
}

