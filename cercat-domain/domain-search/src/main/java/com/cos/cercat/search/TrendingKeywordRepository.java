package com.cos.cercat.search;

import java.util.List;
import java.util.Optional;

public interface TrendingKeywordRepository {
    void setTrendingKeywords(List<String> trendingKeywords);

    List<String> findTrendingKeywords();
}
