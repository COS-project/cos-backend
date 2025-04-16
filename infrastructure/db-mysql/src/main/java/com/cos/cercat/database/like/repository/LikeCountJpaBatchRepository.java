package com.cos.cercat.database.like.repository;

import com.cos.cercat.domain.like.LikeCount;
import java.util.List;

public interface LikeCountJpaBatchRepository {

    void batchUpdate(List<LikeCount> likeCounts);

}
