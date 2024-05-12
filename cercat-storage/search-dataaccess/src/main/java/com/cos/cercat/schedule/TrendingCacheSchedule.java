package com.cos.cercat.schedule;

import com.cos.cercat.cache.TrendingKeywordCacheRepositoryImpl;
import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.repository.CertificateRepositoryImpl;
import com.cos.cercat.repository.PostForForSearchRepositoryImpl;
import com.cos.cercat.search.KeywordStatus;
import com.cos.cercat.search.TrendingKeyword;
import com.cos.cercat.search.TrendingKeywordsCacheManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrendingCacheSchedule {

    private final PostForForSearchRepositoryImpl postForSearchRepository;
    private final TrendingKeywordCacheRepositoryImpl trendingKeywordCacheRepository;
    private final CertificateRepositoryImpl certificateRepository;

    @Scheduled(cron = "0 */5 * * * *")
    @SchedulerLock(name = "trending_cache", lockAtMostFor = "PT2S", lockAtLeastFor = "PT2S")
    public void trendingCache() {
        log.info("Trending Cache Scheduled task executed at {}", LocalDateTime.now());

        List<Certificate> certificates = certificateRepository.findAll();

        for (Certificate certificate : certificates) {
            List<TrendingKeyword> trendingKeywords = new ArrayList<>();
            List<String> currentKeywords = postForSearchRepository.findRecentTop10Keywords(certificate);
            List<TrendingKeyword> beforeKeywords = trendingKeywordCacheRepository.findTrendingKeywords(certificate);

            for (String currentKeyword : currentKeywords) {
                KeywordStatus status = getKeywordStatus(beforeKeywords, currentKeywords, currentKeyword);
                trendingKeywords.add(TrendingKeyword.of(currentKeyword, status));
            }
            trendingKeywordCacheRepository.setTrendingKeywords(certificate, trendingKeywords);
        }
    }

    private KeywordStatus getKeywordStatus(List<TrendingKeyword> beforeTrendingWithStatus,
                                           List<String> currentKeywords,
                                           String currentKeyword) {

        if (beforeTrendingWithStatus == null || beforeTrendingWithStatus.stream().noneMatch(t -> t.keyword().equals(currentKeyword))) {
            return KeywordStatus.NEW;
        }

        int currentRank = currentKeywords.indexOf(currentKeyword);
        int beforeRank = beforeTrendingWithStatus.stream().map(TrendingKeyword::keyword).toList().indexOf(currentKeyword);

        if (currentRank > beforeRank) {
            return KeywordStatus.RANK_DOWN;
        }

        if (currentRank < beforeRank) {
            return KeywordStatus.RANK_UP;
        }
        return KeywordStatus.UNCHANGED;
    }

}
