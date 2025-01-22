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

    public void like(TargetUser targetUser, LikeTarget likeTarget) {
        User user = userReader.read(targetUser);
        likeManager.like(user, likeTarget);
    }

    public void unLike(TargetUser targetUser, LikeTarget likeTarget) {
        User user = userReader.read(targetUser);
        likeManager.unLike(user, likeTarget);
    }

    public LikeStatus getLikeStatus(TargetUser targetUser, LikeTarget likeTarget) {
        User user = userReader.read(targetUser);
        return likeReader.read(user, likeTarget);
    }
}
