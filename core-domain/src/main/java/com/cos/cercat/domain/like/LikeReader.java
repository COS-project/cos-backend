package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeReader {

    private final LikeRepository likeRepository;

    public boolean isLiked(User user, LikeTarget likeTarget) {
        return likeRepository.isLiked(user, likeTarget);
    }

    public LikeStatus read(User user, LikeTarget likeTarget) {
        boolean isLiked = likeRepository.isLiked(user, likeTarget);
        Long likeCount = likeRepository.getCount(likeTarget);
        return LikeStatus.of(likeCount, isLiked);
    }
}
