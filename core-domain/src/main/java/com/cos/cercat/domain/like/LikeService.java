package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeStatusReader likeStatusReader;
    private final LikeManager likeManager;
    private final UserReader userReader;
    private final LikeCounter likeCounter;

    @Transactional
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
        boolean isLiked = likeStatusReader.isLiked(liker, likeTarget);
        return LikeStatus.of(likeCount, isLiked);
    }

    public Map<Long, LikeStatus> getLikeStatusMap(UserId userId, List<LikeTarget> likeTargets) {
        if (likeTargets.isEmpty()) {
            return Map.of();
        }

        User liker = userReader.read(userId);
        Map<Long, LikeCount> likeCountMap = likeCounter.getConutMap(likeTargets);
        return likeStatusReader.readMap(liker, likeTargets, likeCountMap);
    }
}
