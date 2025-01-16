package com.cos.cercat.domain.postsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return List.copyOf(keywords.values());
    }

    public boolean contains(TrendingKeyword keyword) {
        return keywords.containsKey(keyword.keyword());
    }

    public int getRank(String keyword) {
        return keywords.get(keyword).rank();
    }
}
