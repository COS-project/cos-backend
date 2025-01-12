package com.cos.cercat.database.alarm.entity;

import com.cos.cercat.domain.like.LikeTargetType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@DiscriminatorValue("LikeAlarm")
public class LikeAlarmEntity extends AlarmEntity {

    private Long likerId;
    private String likerNickname;

    @Enumerated(EnumType.STRING)
    private LikeTargetType likeTargetType;

}