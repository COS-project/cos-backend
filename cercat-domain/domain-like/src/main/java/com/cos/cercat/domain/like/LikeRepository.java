package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.User;

public interface LikeRepository {

    boolean isLiked(User user, Like like);

    void save(User user, Like like);

    void remove(User user, Like like);
}
