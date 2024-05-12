package com.cos.cercat.search;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        return Optional.ofNullable(trendingKeywordsCacheManager.findTrendingKeywords(certificate))
                .orElseGet(() -> {
                    List<String> recentTop10Keywords = postForSearchRepository.findRecentTop10Keywords(certificate);

                    List<TrendingKeyword> trendingKeywords = recentTop10Keywords.stream()
                            .map(TrendingKeyword::newKeyword)
                            .toList();

                    trendingKeywordsCacheManager.setTrendingKeywords(certificate, trendingKeywords);
                    return trendingKeywords;
                });
    }
}
