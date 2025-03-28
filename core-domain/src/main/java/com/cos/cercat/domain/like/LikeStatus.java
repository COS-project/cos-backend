package com.cos.cercat.domain.like;

public record LikeStatus(
        LikeCount count,
        boolean isLiked
) {
    public static LikeStatus NONE(Long targetId, LikeTargetType targetType) {
        return switch (targetType) {
            case POST -> new LikeStatus(LikeCount.init(LikeTarget.post(targetId)), false);
            case COMMENT -> new LikeStatus(LikeCount.init(LikeTarget.comment(targetId)), false);
        };
    }

    public static LikeStatus of(LikeCount count, boolean isLiked) {
        return new LikeStatus(count, isLiked);
    }
}
