package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikeReader {

    private final CommentLikeRepository commentLikeRepository;

    public boolean isLiked(TargetUser targetUser, TargetComment targetComment) {
        return commentLikeRepository.isLiked(targetUser, targetComment);
    }

}
