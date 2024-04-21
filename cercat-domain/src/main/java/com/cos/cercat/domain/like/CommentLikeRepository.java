package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.TargetUser;

public interface CommentLikeRepository {

    boolean isLiked(TargetUser targetUser, TargetComment targetComment);
}
