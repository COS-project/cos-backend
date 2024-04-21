package com.cos.cercat.domain.like;

import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.TargetUser;

public interface PostLikeRepository {

    boolean isLiked(TargetUser targetUser, TargetPost targetPost);

}
