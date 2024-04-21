package com.cos.cercat.domain.like;

import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeReader postLikeReader;
    private final CommentLikeReader commentLikeReader;

    public boolean isLiked(LikeTargetType likeTargetType, TargetUser targetUser, Long targetId) {
        if (likeTargetType == LikeTargetType.POST) {
            return postLikeReader.isLiked(targetUser, TargetPost.from(targetId));
        } else {
            return commentLikeReader.isLiked(targetUser, TargetComment.from(targetId));
        }
    }
}
