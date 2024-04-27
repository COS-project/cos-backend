package com.cos.cercat.like;

import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeReader postLikeReader;
    private final LikeManager likeManager;

    //TODO: alarmSender 추가
    public void flipLike(TargetUser targetUser, Like like) {
        if (isLiked(targetUser, like)) {
            likeManager.unLike(targetUser, like);
            return;
        }
        likeManager.like(targetUser, like);
    }

    public boolean isLiked(TargetUser targetUser, Like like) {
        return postLikeReader.isLiked(targetUser, like);
    }
}
