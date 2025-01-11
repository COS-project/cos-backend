package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardAlarm extends Alarm {

    private final User fromUser;
    private final Long postId;

    public BoardAlarm(Long alarmId, User receiver, AlarmType alarmType, User fromUser, Long postId,
            LocalDateTime alarmTime) {
        super(alarmId, receiver, alarmType, alarmTime);
        this.fromUser = fromUser;
        this.postId = postId;
    }
}
