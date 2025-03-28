package com.cos.cercat.domain.like;

public record LikeCount(
        LikeTarget likeTarget,
        Long value
) {

    public static LikeCount init(LikeTarget likeTarget) {
        return new LikeCount(likeTarget, 0L);
    }

}
