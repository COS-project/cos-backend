package com.cos.cercat.alarm.domain;

import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiveUser;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    private Boolean isRead;

    public Alarm(User receiveUser, AlarmType alarmType, Boolean isRead) {
        this.receiveUser = receiveUser;
        this.alarmType = alarmType;
        this.isRead = isRead;
    }
}
