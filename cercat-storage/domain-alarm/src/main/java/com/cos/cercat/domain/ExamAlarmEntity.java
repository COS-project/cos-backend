package com.cos.cercat.domain;

import com.cos.cercat.domain.alarm.AlarmType;
import jakarta.persistence.DiscriminatorValue;
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
@DiscriminatorValue("ExamAlarm")
public class ExamAlarmEntity extends AlarmEntity {

    @ManyToOne
    @JoinColumn(name = "certificate_exam_id")
    private CertificateExamEntity certificateExamEntity;

    @Builder
    public ExamAlarmEntity(UserEntity receiveUserEntity, AlarmType alarmType, Boolean isRead, CertificateExamEntity certificateExamEntity) {
        super(receiveUserEntity, alarmType, isRead);
        this.certificateExamEntity = certificateExamEntity;
    }
}
