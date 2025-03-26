package com.cos.cercat.domain.searchlog;

public record TrendingKeyword(
        int rank,
        String keyword,
        KeywordStatus status
) {

    public static TrendingKeyword newKeyword(int rank, String keyword) {
        return new TrendingKeyword(rank, keyword, KeywordStatus.NEW);
    }

    public TrendingKeyword updateStatus(KeywordStatus keywordStatus) {
        return new TrendingKeyword(rank, keyword, keywordStatus);
    }
}
