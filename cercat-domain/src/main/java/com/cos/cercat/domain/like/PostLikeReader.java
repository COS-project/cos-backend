package com.cos.cercat.domain.like;

import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeReader {

    private final PostLikeRepository postLikeRepository;

    public boolean isLiked(TargetUser targetUser, TargetPost targetPost) {
        return postLikeRepository.isLiked(targetUser, targetPost);
    }

}
