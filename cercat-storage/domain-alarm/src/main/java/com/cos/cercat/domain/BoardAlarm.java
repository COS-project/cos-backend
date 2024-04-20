package com.cos.cercat.domain;

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
    private UserEntity fromUserEntity;

    private Long postId;

    @Builder
    public BoardAlarm(UserEntity receiveUserEntity, AlarmType alarmType, Boolean isRead, UserEntity fromUserEntity, Long postId) {
        super(receiveUserEntity, alarmType, isRead);
        this.fromUserEntity = fromUserEntity;
        this.postId = postId;
    }
}
