package com.cos.cercat.like;


import com.cos.cercat.user.TargetUser;

public interface LikeRepository {

    boolean
    isLiked(TargetUser targetUser, Like like);

    void save(TargetUser targetUser, Like like);

    void remove(TargetUser targetUser, Like like);
}
