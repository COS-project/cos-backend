package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeStatusReader {

    private final LikeRepository likeRepository;

    public boolean isLiked(User liker, LikeTarget likeTarget) {
        return likeRepository.exists(Like.from(liker, likeTarget));
    }

    public Map<Long, Boolean> isLikedMap(
            User liker,
            List<LikeTarget> likeTargets
    ) {
        return likeRepository.existsMap(
                liker,
                likeTargets
        );
    }

    public Map<Long, LikeStatus> readMap(
            User liker,
            List<LikeTarget> likeTargets,
            Map<Long, LikeCount> likeCountMap
    ) {
        Map<Long, Boolean> isLikedMap = isLikedMap(liker, likeTargets);

        return likeTargets.stream()
                .collect(Collectors.toMap(
                        LikeTarget::targetId,
                        target -> {
                            LikeCount likeCount = likeCountMap.getOrDefault(target.targetId(), null);
                            boolean isLiked = isLikedMap.getOrDefault(target.targetId(), false);

                            if (likeCount == null) {
                                return LikeStatus.NONE(target.targetId(), target.targetType());
                            }

                            return LikeStatus.of(likeCount, isLiked);
                        }
                ));
    }

}
