package com.cos.cercat.domain.like;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LikeCountRepository {

    void save(LikeCount likeCount);

    Optional<LikeCount> findByTarget(LikeTarget likeTarget);

    void update(List<LikeCount> bufferedCounts);

    Map<Long, LikeCount> findByTargets(List<LikeTarget> likeTargets);

    void delete(LikeTarget target);
}
