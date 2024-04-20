package com.cos.cercat.domain;

import com.cos.cercat.entity.BaseTimeEntity;
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
    private UserEntity receiveUserEntity;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    private Boolean isRead;

    public Alarm(UserEntity receiveUserEntity, AlarmType alarmType, Boolean isRead) {
        this.receiveUserEntity = receiveUserEntity;
        this.alarmType = alarmType;
        this.isRead = isRead;
    }
}
