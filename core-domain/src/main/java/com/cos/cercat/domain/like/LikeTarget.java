package com.cos.cercat.domain.like;

public record LikeTarget(
        Long targetId,
        LikeTargetType targetType
) {
    public static LikeTarget post(Long targetId) {
        return new LikeTarget(
                targetId,
                LikeTargetType.POST
        );
    }

    public static LikeTarget comment(Long targetId) {
        return new LikeTarget(
                targetId,
                LikeTargetType.COMMENT
        );
    }
}
