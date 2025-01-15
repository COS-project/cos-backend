package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeReader postLikeReader;
    private final LikeManager likeManager;
    private final UserReader userReader;

    public void flipLike(TargetUser targetUser, Like like) {
        User user = userReader.read(targetUser);
        if (postLikeReader.isLiked(user, like)) {
            likeManager.unLike(user, like);
            return;
        }
        likeManager.like(user, like);
    }

    public boolean isLiked(TargetUser targetUser, Like like) {
        User user = userReader.read(targetUser);
        return postLikeReader.isLiked(user, like);
    }
}
