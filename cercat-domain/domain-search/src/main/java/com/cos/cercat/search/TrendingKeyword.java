package com.cos.cercat.search;

public record TrendingKeyword(
        String keyword,
        KeywordStatus status
) {
    public static TrendingKeyword of(String keyword, KeywordStatus status) {
        return new TrendingKeyword(keyword, status);
    }
}
