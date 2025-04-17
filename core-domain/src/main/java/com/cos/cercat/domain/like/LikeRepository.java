package com.cos.cercat.domain.like;

import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Map;

public interface LikeRepository {

    boolean exists(Like like);

    Map<Long, Boolean> existsMap(
            User liker,
            List<LikeTarget> likeTargets
    );

    void save(Like like);

    void remove(Like like);

}
