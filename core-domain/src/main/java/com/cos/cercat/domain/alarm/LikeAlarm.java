package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.like.LikeTargetType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class LikeAlarm extends Alarm {

    private final Long likerId;
    private final String likerNickname;
    private final LikeTargetType likeTargetType;

}
