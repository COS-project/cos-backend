package com.cos.cercat.alarm.domain;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Getter
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiveUser;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Builder
    public Alarm(User receiveUser, User fromUser, Long targetId, AlarmType alarmType) {
        this.receiveUser = receiveUser;
        this.fromUser = fromUser;
        this.targetId = targetId;
        this.alarmType = alarmType;
    }
}
