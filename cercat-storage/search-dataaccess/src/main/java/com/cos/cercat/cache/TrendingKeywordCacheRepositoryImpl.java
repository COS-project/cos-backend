package com.cos.cercat.cache;

import com.cos.cercat.search.TrendingKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TrendingKeywordCacheRepositoryImpl implements TrendingKeywordRepository {

    private final static String TRENDING_KEYWORDS_KEY = "TRENDING_KEYWORDS";
    private final RedisTemplate<String, List<String>> redisTemplate;

    @Override
    public void setTrendingKeywords(List<String> trendingKeywords) {
        redisTemplate.opsForValue().set(TRENDING_KEYWORDS_KEY, trendingKeywords);
    }

    @Override
    public List<String> findTrendingKeywords() {
        List<String> trendingKeywords = redisTemplate.opsForValue().get(TRENDING_KEYWORDS_KEY);
        log.info("Get Trending Keywords : {}", trendingKeywords);
        return trendingKeywords;
    }
}
