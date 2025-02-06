package com.cos.cercat.redis.postsearch;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.postsearch.TrendingKeywordsRanking;
import com.cos.cercat.domain.postsearch.TrendingKeywordRankingCache;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisTrendingKeywordRankingCache implements TrendingKeywordRankingCache {

    private final RedisTemplate<String, TrendingKeywordsRanking> redisTemplate;

    @Override
    public void cache(Certificate certificate, TrendingKeywordsRanking trendingKeywords) {
        String key = getKey(certificate);
        log.info("Set Trending Keywords {} : {}", key, trendingKeywords);
        redisTemplate.opsForValue().set(key, trendingKeywords);
    }

    @Override
    public Optional<TrendingKeywordsRanking> find(Certificate certificate) {
        String key = getKey(certificate);
        TrendingKeywordsRanking trendingKeywordsRanking = redisTemplate.opsForValue().get(key);
        log.info("Get Trending Keywords {} : {}", key, trendingKeywordsRanking);
        return Optional.ofNullable(trendingKeywordsRanking);
    }

    private String getKey(Certificate certificate) {
        return "CERTIFICATE_TRENDING:" + certificate.id();
    }
}
