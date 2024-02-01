package com.cos.cercat.like.app;

import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class LikeStrategyFactory {

    private final Map<StrategyName, LikeStrategy<?>> strategies;

    @Autowired
    public LikeStrategyFactory(Set<LikeStrategy<?>> strategySet) {
        this.strategies = createStrategy(strategySet);
    }

    @SuppressWarnings("unchecked")
    public <T> LikeStrategy<T> findStrategy(StrategyName strategyName, Class<T> type) {
        LikeStrategy<?> likeStrategy = strategies.get(strategyName);
        if (type.isAssignableFrom(likeStrategy.getGenericType())) {
            return (LikeStrategy<T>) likeStrategy;
        }
        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private Map<StrategyName, LikeStrategy<?>> createStrategy(Set<LikeStrategy<?>> strategySet) {
        Map<StrategyName, LikeStrategy<?>> strategyMap = new HashMap<>();
        strategySet.forEach(strategy -> strategyMap.put(strategy.getStrategyName(), strategy));
        return strategyMap;
    }
}
