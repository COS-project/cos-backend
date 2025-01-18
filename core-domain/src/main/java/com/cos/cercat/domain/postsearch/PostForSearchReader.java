package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostForSearchReader {

    private final PostForSearchRepository postForSearchRepository;
    private final TrendingKeywordRankingCache trendingKeywordRankingCache;

    public SliceResult<PostForSearch> read(SearchCond cond, Certificate certificate, Cursor cursor) {
        return postForSearchRepository.search(cond, certificate, cursor);
    }

    public List<String> readAutoCompletedKeywords(Certificate certificate, String searchText) {
        return postForSearchRepository.findAutoCompletedKeywords(certificate, searchText);
    }

    public TrendingKeywordsRanking readTrendingKeywordRanking(Certificate certificate) {
        return trendingKeywordRankingCache.find(certificate).orElseGet(() -> {
            TrendingKeywordsRanking trendingKeywords = readCurrentTrendingKeywordRanking(certificate);
            trendingKeywordRankingCache.cache(certificate, trendingKeywords);
            return trendingKeywords;
        });
    }

    public TrendingKeywordsRanking readCurrentTrendingKeywordRanking(Certificate certificate) {
        List<String> recentTop10Keywords = postForSearchRepository.findRecentTrendingKeywords(certificate);
        return TrendingKeywordsRanking.from(recentTop10Keywords);
    }
}
