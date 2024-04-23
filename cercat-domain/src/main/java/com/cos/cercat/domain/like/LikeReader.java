package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeReader {

    private final LikeRepository likeRepository;

    public boolean isLiked(TargetUser targetUser, Like like) {
        return likeRepository.isLiked(targetUser, like);
    }

}
