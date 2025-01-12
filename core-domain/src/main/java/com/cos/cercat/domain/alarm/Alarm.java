package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.common.Event;
import com.cos.cercat.domain.like.LikeCreatedEvent;
import com.cos.cercat.domain.post.CommentCreatedEvent;
import com.cos.cercat.domain.user.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Alarm {

    private final Long id;
    private final User receiver;
    private final Long originId;
    private final AlarmType alarmType;
    private final LocalDateTime alarmTime;
    private final boolean isRead = false;

    public static Alarm from(Event event) {
        return switch (event.getType()) {
            case "like-created-event": {
                LikeCreatedEvent likeCreatedEvent = (LikeCreatedEvent) event;
                yield LikeAlarm.builder()
                        .originId(likeCreatedEvent.targetId())
                        .likeTargetType(likeCreatedEvent.targetType())
                        .receiver(likeCreatedEvent.targetOwner())
                        .likerId(likeCreatedEvent.likerId())
                        .likerNickname(likeCreatedEvent.likerNickname())
                        .alarmType(AlarmType.NEW_LIKE_ON_POST)
                        .alarmTime(LocalDateTime.now())
                        .build();
            }

            case "comment-created-event": {
                CommentCreatedEvent commentCreatedEvent = (CommentCreatedEvent) event;
                yield Alarm.builder()
                        .originId(commentCreatedEvent.targetPostId())
                        .receiver(commentCreatedEvent.targetPostOwner())
                        .alarmType(AlarmType.NEW_COMMENT_ON_POST)
                        .alarmTime(LocalDateTime.now())
                        .build();
            }
            default:
                throw new IllegalStateException("Unexpected value: " + event.getType());
        };
    }

}
