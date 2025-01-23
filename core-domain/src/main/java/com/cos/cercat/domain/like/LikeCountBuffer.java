package com.cos.cercat.domain.like;

import java.util.List;

public interface LikeCountBuffer {

    void countUp(LikeTarget likeTarget);

    void countDown(LikeTarget likeTarget);

    List<LikeCount> getAll();
}
