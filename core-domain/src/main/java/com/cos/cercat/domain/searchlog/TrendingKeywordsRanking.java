package com.cos.cercat.domain.searchlog;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record TrendingKeywordsRanking(
        Map<String, TrendingKeyword> keywords
) {

    public static TrendingKeywordsRanking from(Map<String, TrendingKeyword> keywords) {
        return new TrendingKeywordsRanking(keywords);
    }
    public static TrendingKeywordsRanking from(List<String> trendingKeywords) {
        Map<String, TrendingKeyword> keywordMap = new HashMap<>();
        for (int i = 0; i < trendingKeywords.size(); i++) {
            String keyword = trendingKeywords.get(i);
            keywordMap.put(keyword, TrendingKeyword.newKeyword(i + 1, keyword));
        }
        return new TrendingKeywordsRanking(keywordMap);
    }

    public List<TrendingKeyword> getKeywordList() {
        return keywords.values().stream()
                .sorted(Comparator.comparingInt(TrendingKeyword::rank))
                .collect(Collectors.toList());
    }


}
