package com.cos.cercat.domain;


import com.cos.cercat.domain.alarm.*;
import com.cos.cercat.entity.BaseTimeEntity;
import com.cos.cercat.exception.EntityTypeMismatchException;
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
@Table(name = "alarm")
@DiscriminatorValue("Alarm")
public class AlarmEntity extends BaseTimeEntity {

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

    public AlarmEntity(UserEntity receiveUserEntity, AlarmType alarmType, Boolean isRead) {
        this.receiveUserEntity = receiveUserEntity;
        this.alarmType = alarmType;
        this.isRead = isRead;
    }

    public Alarm toDomain() {
        if (this instanceof BoardAlarmEntity boardAlarm) {
            return new BoardAlarm(
                    receiveUserEntity.toDomain(),
                    alarmType,
                    boardAlarm.getFromUserEntity().toDomain(),
                    boardAlarm.getPostId()
            );
        }

        if (this instanceof ExamAlarmEntity examAlarm) {
            return new ExamAlarm(
                    receiveUserEntity.toDomain(),
                    alarmType,
                    examAlarm.getCertificateExamEntity().toDomain()
            );
        }

        throw EntityTypeMismatchException.EXCEPTION;
    }

    public static AlarmEntity from(AlarmEvent alarmEvent) {
        return new BoardAlarmEntity(
                UserEntity.from(alarmEvent.recieveUser()),
                alarmEvent.alarmType(),
                false,
                UserEntity.from(alarmEvent.alarmArg().fromUser()),
                alarmEvent.alarmArg().targetId()
        );
    }
}

