package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeReader likeReader;
    private final LikeManager likeManager;
    private final UserReader userReader;
    private final LikeCounter likeCounter;

    public void like(UserId userId, LikeTarget likeTarget) {
        User liker = userReader.read(userId);
        likeManager.like(liker, likeTarget);
    }

    public void unLike(UserId userId, LikeTarget likeTarget) {
        User liker = userReader.read(userId);
        likeManager.unLike(liker, likeTarget);
    }

    public LikeStatus getLikeStatus(UserId userId, LikeTarget likeTarget) {
        User liker = userReader.read(userId);
        LikeCount likeCount = likeCounter.get(likeTarget);
        boolean isLiked = likeReader.isLiked(liker, likeTarget);
        return LikeStatus.of(likeCount, isLiked);
    }
}
