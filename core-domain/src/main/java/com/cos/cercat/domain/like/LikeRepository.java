package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.User;

public interface LikeRepository {

    boolean isLiked(User user, LikeTarget likeTarget);

    void save(User user, LikeTarget likeTarget);

    void remove(User user, LikeTarget likeTarget);

    void initLikeCount(LikeTarget likeTarget);

    Long getCount(LikeTarget likeTarget);

}
