package com.cos.cercat.search;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostForSearchReader {

    private final PostForSearchRepository postForSearchRepository;
    private final TrendingKeywordsCacheManager trendingKeywordsCacheManager;


    public SliceResult<PostForSearch> read(SearchCond cond, Certificate certificate, Cursor cursor) {
        return postForSearchRepository.search(cond, certificate, cursor);
    }

    public List<String> readAutoCompletedKeywords(Certificate certificate, String searchText) {
        return postForSearchRepository.findAutoCompletedKeywords(certificate, searchText);
    }

    public List<TrendingKeyword> readTrendingKeywords(Certificate certificate) {
        List<TrendingKeyword> trendingKeywords = new ArrayList<>();
        List<String> currentKeywords = postForSearchRepository.findRecentTop10Keywords(certificate);
        List<String> beforeKeywords = trendingKeywordsCacheManager.findTrendingKeywords();

        for (String currentKeyword : currentKeywords) {
            KeywordStatus status = getKeywordStatus(beforeKeywords, currentKeywords, currentKeyword);
            trendingKeywords.add(TrendingKeyword.of(currentKeyword, status));
        }
        trendingKeywordsCacheManager.setTrendingKeywords(currentKeywords);
        return trendingKeywords;
    }

    private KeywordStatus getKeywordStatus(List<String> beforeKeywords,
                                           List<String> currentKeywords,
                                           String currentKeyword) {
        if (beforeKeywords == null || !beforeKeywords.contains(currentKeyword)) {
            return KeywordStatus.NEW;
        }
        int currentRank = currentKeywords.indexOf(currentKeyword);
        int beforeRank = beforeKeywords.indexOf(currentKeyword);

        if (currentRank > beforeRank) {
            return KeywordStatus.RANK_DOWN;
        } else if (currentRank < beforeRank) {
            return KeywordStatus.RANK_UP;
        }
        return KeywordStatus.UNCHANGED;
    }
}
