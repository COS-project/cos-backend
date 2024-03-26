package com.cos.cercat.apis.like.app.strategy;

import com.cos.cercat.domain.User;

public interface LikeStrategy<T> {
    void flipLike(T entity, User user);

    StrategyName getStrategyName();

    Class<T> getGenericType();
}
