package com.cos.cercat.domain.like;

public record LikeCount(
        LikeTarget likeTarget,
        Long count
) {

    public static LikeCount init(LikeTarget likeTarget) {
        return new LikeCount(likeTarget, 0L);
    }

}
