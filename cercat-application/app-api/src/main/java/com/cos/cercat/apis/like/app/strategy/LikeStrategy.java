package com.cos.cercat.apis.like.app.strategy;

import com.cos.cercat.domain.UserEntity;

public interface LikeStrategy<T> {
    void flipLike(T entity, UserEntity userEntity);

    StrategyName getStrategyName();

    Class<T> getGenericType();
}
