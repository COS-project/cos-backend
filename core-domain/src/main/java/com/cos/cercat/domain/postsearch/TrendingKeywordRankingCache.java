package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.certificate.Certificate;

import java.util.Optional;

public interface TrendingKeywordRankingCache {
    void cache(Certificate certificate, TrendingKeywordsRanking trendingKeywords);

    Optional<TrendingKeywordsRanking> find(Certificate certificate);
}
