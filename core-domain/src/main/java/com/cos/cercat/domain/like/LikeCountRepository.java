package com.cos.cercat.domain.like;

import java.util.List;
import java.util.Optional;

public interface LikeCountRepository {

    void save(LikeCount likeCount);

    Optional<LikeCount> findByTarget(LikeTarget likeTarget);

    void saveAll(List<LikeCount> bufferedCounts);

}
