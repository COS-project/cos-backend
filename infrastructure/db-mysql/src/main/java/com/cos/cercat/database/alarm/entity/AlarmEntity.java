package com.cos.cercat.database.alarm.entity;

import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.alarm.Alarm;
import com.cos.cercat.domain.alarm.AlarmType;
import com.cos.cercat.domain.alarm.LikeAlarm;
import com.cos.cercat.domain.alarm.ExamAlarm;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Table(name = "alarm")
@SuperBuilder
@DiscriminatorValue("Alarm")
public class AlarmEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity receiver;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    private Long originId;

    private Boolean isRead;

    public static AlarmEntity from(Alarm domain) {
        if (domain instanceof LikeAlarm likeAlarm) {
            return LikeAlarmEntity.builder()
                    .id(likeAlarm.getId())
                    .receiver(UserEntity.from(likeAlarm.getReceiver()))
                    .alarmType(likeAlarm.getAlarmType())
                    .originId(likeAlarm.getOriginId())
                    .isRead(domain.isRead())
                    .likerId(likeAlarm.getLikerId())
                    .likerNickname(likeAlarm.getLikerNickname())
                    .likeTargetType(likeAlarm.getLikeTargetType())
                    .build();
        }

        if (domain instanceof ExamAlarm examAlarm) {
            return ExamAlarmEntity.builder()
                    .id(examAlarm.getId())
                    .receiver(UserEntity.from(examAlarm.getReceiver()))
                    .alarmType(examAlarm.getAlarmType())
                    .originId(examAlarm.getOriginId())
                    .isRead(examAlarm.isRead())
                    .certificateId(examAlarm.getCertificate().id().value())
                    .certificateExamName(examAlarm.getCertificate().certificateName())
                    .build();
        }

        return AlarmEntity.builder()
                .id(domain.getId())
                .receiver(UserEntity.from(domain.getReceiver()))
                .alarmType(domain.getAlarmType())
                .originId(domain.getOriginId())
                .isRead(domain.isRead())
                .build();
    }

    public Alarm toDomain() {
        if (this instanceof LikeAlarmEntity likeAlarmEntity) {
            return LikeAlarm.builder()
                    .id(id)
                    .receiver(receiver.toDomain())
                    .alarmType(alarmType)
                    .originId(likeAlarmEntity.getOriginId())
                    .alarmTime(getCreatedAt())
                    .likerId(likeAlarmEntity.getLikerId())
                    .likerNickname(likeAlarmEntity.getLikerNickname())
                    .likeTargetType(likeAlarmEntity.getLikeTargetType())
                    .build();
        }

        if (this instanceof ExamAlarmEntity examAlarmEntity) {
            return ExamAlarm.builder()
                    .id(id)
                    .receiver(receiver.toDomain())
                    .alarmType(alarmType)
                    .originId(examAlarmEntity.getOriginId())
                    .alarmTime(getCreatedAt())
                    .certificate(
                            new Certificate(
                                    CertificateId.from(examAlarmEntity.getCertificateId()),
                                    examAlarmEntity.getCertificateExamName()
                            )
                    )
                    .build();
        }

        return Alarm.builder()
                .id(id)
                .receiver(receiver.toDomain())
                .alarmType(alarmType)
                .originId(originId)
                .alarmTime(getCreatedAt())
                .build();
    }

}

