package com.cos.cercat.domain.like;

public interface LikeRepository {

    boolean exists(Like like);

    void save(Like like);

    void remove(Like like);

}
