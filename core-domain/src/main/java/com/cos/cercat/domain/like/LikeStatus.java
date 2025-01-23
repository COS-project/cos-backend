package com.cos.cercat.domain.like;

public record LikeStatus(
        LikeCount count,
        boolean isLiked
) {

    public static LikeStatus of(LikeCount count, boolean isLiked) {
        return new LikeStatus(count, isLiked);
    }
}
