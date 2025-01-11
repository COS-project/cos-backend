package com.cos.cercat.search.postsearch.scheduler;

import com.cos.cercat.cache.postsearch.TrendingKeywordCacheRepositoryImpl;
import com.cos.cercat.database.certificate.repository.CertificateRepositoryImpl;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.postsearch.KeywordStatus;
import com.cos.cercat.domain.postsearch.TrendingKeyword;
import com.cos.cercat.search.postsearch.repository.PostForForSearchRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrendingCacheScheduler {

    private final PostForForSearchRepositoryImpl postForSearchRepository;
    private final TrendingKeywordCacheRepositoryImpl trendingKeywordCacheRepository;
    private final CertificateRepositoryImpl certificateRepository;

    @Scheduled(cron = "0 */5 * * * *")
    @SchedulerLock(name = "trending_cache", lockAtMostFor = "PT2S", lockAtLeastFor = "PT2S")
    public void trendingCache() {
        List<Certificate> certificates = certificateRepository.findAll();

        for (Certificate certificate : certificates) {
            List<TrendingKeyword> trendingKeywords = new ArrayList<>();
            List<String> currentKeywords = postForSearchRepository.findRecentTop10Keywords(
                    certificate);
            List<TrendingKeyword> beforeKeywords = trendingKeywordCacheRepository.findTrendingKeywords(
                    certificate);

            for (String currentKeyword : currentKeywords) {
                KeywordStatus status = getKeywordStatus(beforeKeywords, currentKeywords,
                        currentKeyword);
                trendingKeywords.add(TrendingKeyword.of(currentKeyword, status));
            }
            trendingKeywordCacheRepository.setTrendingKeywords(certificate, trendingKeywords);
        }
    }

    private KeywordStatus getKeywordStatus(List<TrendingKeyword> beforeTrendingWithStatus,
            List<String> currentKeywords,
            String currentKeyword) {

        if (beforeTrendingWithStatus == null || beforeTrendingWithStatus.stream()
                .noneMatch(t -> t.keyword().equals(currentKeyword))) {
            return KeywordStatus.NEW;
        }

        int currentRank = currentKeywords.indexOf(currentKeyword);
        int beforeRank = beforeTrendingWithStatus.stream().map(TrendingKeyword::keyword).toList()
                .indexOf(currentKeyword);

        if (currentRank > beforeRank) {
            return KeywordStatus.RANK_DOWN;
        }

        if (currentRank < beforeRank) {
            return KeywordStatus.RANK_UP;
        }
        return KeywordStatus.UNCHANGED;
    }

}
