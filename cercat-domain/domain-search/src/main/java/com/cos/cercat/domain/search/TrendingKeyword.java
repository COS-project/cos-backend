package com.cos.cercat.domain.search;

public record TrendingKeyword(
        String keyword,
        KeywordStatus status
) {
    public static TrendingKeyword of(String keyword, KeywordStatus status) {
        return new TrendingKeyword(keyword, status);
    }

    public static TrendingKeyword newKeyword(String keyword) {
        return new TrendingKeyword(keyword, KeywordStatus.NEW);
    }
}
