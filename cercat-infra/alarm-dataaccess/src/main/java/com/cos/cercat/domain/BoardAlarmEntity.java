package com.cos.cercat.domain;

import com.cos.cercat.domain.alarm.AlarmType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("BoardAlarm")
public class BoardAlarmEntity extends AlarmEntity {

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    @OnDelete(action = CASCADE)
    private UserEntity fromUserEntity;

    private Long postId;

    public BoardAlarmEntity(UserEntity receiveUserEntity, AlarmType alarmType, Boolean isRead, UserEntity fromUserEntity, Long postId) {
        super(receiveUserEntity, alarmType, isRead);
        this.fromUserEntity = fromUserEntity;
        this.postId = postId;
    }
}
