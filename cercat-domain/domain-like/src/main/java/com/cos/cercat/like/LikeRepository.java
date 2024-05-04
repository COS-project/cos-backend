package com.cos.cercat.like;

import com.cos.cercat.user.User;

public interface LikeRepository {

    boolean isLiked(User user, Like like);

    void save(User user, Like like);

    void remove(User user, Like like);
}
