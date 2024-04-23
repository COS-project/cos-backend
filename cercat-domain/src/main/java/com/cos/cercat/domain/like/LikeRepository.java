package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.TargetUser;

public interface LikeRepository {

    boolean
    isLiked(TargetUser targetUser, Like like);

    void save(TargetUser targetUser, Like like);

    void remove(TargetUser targetUser, Like like);
}
