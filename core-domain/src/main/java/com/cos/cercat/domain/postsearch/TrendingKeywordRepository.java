package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.certificate.Certificate;

import java.util.List;

public interface TrendingKeywordRepository {
    void setTrendingKeywords(Certificate certificate, List<TrendingKeyword> trendingKeywords);

    List<TrendingKeyword> findTrendingKeywords(Certificate certificate);
}
