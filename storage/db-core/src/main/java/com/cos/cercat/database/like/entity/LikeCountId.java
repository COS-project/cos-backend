package com.cos.cercat.database.like.entity;

import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeTargetType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record LikeCountId(
        Long targetId,
        @Enumerated(EnumType.STRING)
        LikeTargetType targetType
) {

    public static LikeCountId from(LikeTarget likeTarget) {
        return new LikeCountId(likeTarget.targetId(), likeTarget.targetType());
    }
}
