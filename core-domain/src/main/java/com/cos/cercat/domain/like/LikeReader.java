package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeReader {

    private final LikeRepository likeRepository;

    public boolean isLiked(User liker, LikeTarget likeTarget) {
        return likeRepository.exists(Like.from(liker, likeTarget));
    }
}
