package com.cos.cercat.database.alarm.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@DiscriminatorValue("ExamAlarm")
public class ExamAlarmEntity extends AlarmEntity {

    private Long certificateId;
    private String certificateExamName;

}
