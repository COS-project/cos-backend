package com.cos.cercat.database.like.repository;

import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Map;

public interface LikeJpaCustomRepository {

    Map<Long, Boolean> existsMap(
            User liker,
            List<LikeTarget> likeTargets
    );

}
