package com.cos.cercat.cache;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.search.TrendingKeyword;
import com.cos.cercat.search.TrendingKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TrendingKeywordCacheRepositoryImpl implements TrendingKeywordRepository {

    private final RedisTemplate<String, List<TrendingKeyword>> redisTemplate;

    @Override
    public void setTrendingKeywords(Certificate certificate, List<TrendingKeyword> trendingKeywords) {
        String key = getKey(certificate);
        log.info("Set Trending Keywords {} : {}", key, trendingKeywords);
        redisTemplate.opsForValue().set(key, trendingKeywords);
    }

    @Override
    public List<TrendingKeyword> findTrendingKeywords(Certificate certificate) {
        String key = getKey(certificate);
        List<TrendingKeyword> trendingKeywords = redisTemplate.opsForValue().get(key);
        log.info("Get Trending Keywords {} : {}", key, trendingKeywords);
        return trendingKeywords;
    }

    private String getKey(Certificate certificate) {
        return "CERTIFICATE_TRENDING:" + certificate.id();
    }
}
