package com.cos.cercat.domain.like;

public record Like(
        Long targetId,
        LikeTargetType targetType
) {
    public static Like from(Long targetId, LikeTargetType targetType) {
        return new Like(
                targetId,
                targetType
        );
    }
}
