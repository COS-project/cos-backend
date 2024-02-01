package com.cos.cercat.like.app;

import com.cos.cercat.user.domain.User;

public interface LikeStrategy<T> {
    void flipLike(T entity, User user);

    StrategyName getStrategyName();

    Class<T> getGenericType();
}