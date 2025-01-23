package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.TargetUser;
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

    public void like(TargetUser targetUser, LikeTarget likeTarget) {
        User liker = userReader.read(targetUser);
        likeManager.like(liker, likeTarget);
    }

    public void unLike(TargetUser targetUser, LikeTarget likeTarget) {
        User liker = userReader.read(targetUser);
        likeManager.unLike(liker, likeTarget);
    }

    public LikeStatus getLikeStatus(TargetUser targetUser, LikeTarget likeTarget) {
        User liker = userReader.read(targetUser);
        LikeCount likeCount = likeCounter.get(likeTarget);
        boolean isLiked = likeReader.isLiked(liker, likeTarget);
        return LikeStatus.of(likeCount, isLiked);
    }
}
