package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrendingKeywordRankingService {

    private final CertificateReader certificateReader;
    private final PostForSearchReader postForSearchReader;
    private final TrendingKeywordRankingCache trendingKeywordRankingCache;

    public void refreshRanking() {
        List<Certificate> allCertificates = certificateReader.readAll();
        for (Certificate certificate : allCertificates) {
            processCertificateTrendingKeywords(certificate);
        }
    }

    private void processCertificateTrendingKeywords(Certificate certificate) {
        Optional<TrendingKeywordsRanking> beforeRanking = trendingKeywordRankingCache.find(certificate);
        TrendingKeywordsRanking currentRanking = postForSearchReader.readCurrentTrendingKeywordRanking(certificate);
        TrendingKeywordsRanking updatedRanking = getUpdatedRanking(currentRanking, beforeRanking);
        trendingKeywordRankingCache.cache(certificate, updatedRanking);
    }

    private TrendingKeywordsRanking getUpdatedRanking(
            TrendingKeywordsRanking currentRanking,
            Optional<TrendingKeywordsRanking> beforeRanking
    ) {
        Map<String, TrendingKeyword> updatedKeywordMap = new HashMap<>();

        for (TrendingKeyword targetKeyword : currentRanking.getKeywordList()) {
            TrendingKeyword updatedKeyword = applyRankChanges(targetKeyword, beforeRanking.orElse(null));
            updatedKeywordMap.put(updatedKeyword.keyword(), updatedKeyword);
        }
        return TrendingKeywordsRanking.from(updatedKeywordMap);
    }

    private TrendingKeyword applyRankChanges(TrendingKeyword targetKeyword, TrendingKeywordsRanking beforeRanking) {
        if (beforeRanking == null || !beforeRanking.contains(targetKeyword)) {
            return targetKeyword.updateStatus(KeywordStatus.NEW);
        }

        int currentRank = targetKeyword.rank();
        int previousRank = beforeRanking.getRank(targetKeyword.keyword());

        if (currentRank > previousRank) {
            return targetKeyword.updateStatus(KeywordStatus.RANK_DOWN);
        } else if (currentRank < previousRank) {
            return targetKeyword.updateStatus(KeywordStatus.RANK_UP);
        }

        return targetKeyword.updateStatus(KeywordStatus.UNCHANGED);
    }
}


