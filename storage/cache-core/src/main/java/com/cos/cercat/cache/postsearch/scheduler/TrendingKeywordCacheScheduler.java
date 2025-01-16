package com.cos.cercat.cache.postsearch.scheduler;

import com.cos.cercat.domain.postsearch.TrendingKeywordRankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class TrendingKeywordCacheScheduler {

    private final TrendingKeywordRankingService trendingKeywordRankingService;

    @Scheduled(cron = "0 */10 * * * *")
    @SchedulerLock(name = "trending_cache", lockAtMostFor = "PT2S", lockAtLeastFor = "PT2S")
    public void trendingCache() {
        trendingKeywordRankingService.refreshRanking();
    }
}
