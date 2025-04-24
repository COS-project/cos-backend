package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.User;

public record Like(
        Long likerId,
        LikeTarget likeTarget
) {
    public static Like from(User liker, LikeTarget likeTarget) {
        return new Like(liker.getId(), likeTarget);
    }
}
