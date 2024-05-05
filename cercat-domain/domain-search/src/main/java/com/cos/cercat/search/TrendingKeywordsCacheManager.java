package com.cos.cercat.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrendingKeywordsCacheManager {

    private final TrendingKeywordRepository trendingKeywordRepository;

    public void setTrendingKeywords(List<String> trendingKeywords) {
        trendingKeywordRepository.setTrendingKeywords(trendingKeywords);
    }

    public List<String> findTrendingKeywords() {
        return trendingKeywordRepository.findTrendingKeywords();
    }

}
