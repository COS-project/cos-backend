package com.cos.cercat.domain.alarm.domain;

import com.cos.cercat.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardAlarm extends Alarm {

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    private Long postId;

    @Builder
    public BoardAlarm(User receiveUser, AlarmType alarmType, Boolean isRead, User fromUser, Long postId) {
        super(receiveUser, alarmType, isRead);
        this.fromUser = fromUser;
        this.postId = postId;
    }
}
