package com.cos.cercat.domain.like;

public record LikeStatus(
        Long likeCount,
        Boolean isLiked
) {

    public static LikeStatus of(Long likeCount, boolean isLiked) {
        return new LikeStatus(likeCount, isLiked);
    }
}
