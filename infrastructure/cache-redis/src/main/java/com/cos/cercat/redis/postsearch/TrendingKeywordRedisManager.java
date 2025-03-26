package com.cos.cercat.redis.postsearch;

import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.searchlog.KeywordStatus;
import com.cos.cercat.domain.searchlog.SearchLog;
import com.cos.cercat.domain.searchlog.TrendingKeyword;
import com.cos.cercat.domain.searchlog.TrendingKeywordManager;
import com.cos.cercat.domain.searchlog.TrendingKeywordsRanking;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrendingKeywordRedisManager implements TrendingKeywordManager {

    private final StringRedisTemplate redisTemplate;

    private static final int TRENDING_LIMIT = 10;
    private static final double DECAY_FACTOR = 0.8;
    private static final double MIN_SCORE = 0.1;
    private static final int RECENT_WINDOW_SECONDS = 1800; // 30분
    private static final String REALTIME_KEY_PREFIX = "trending:realtime:";
    private static final String COUNT_KEY_PREFIX = "trending:count:";
    private static final String PREVIOUS_KEY_PREFIX = "trending:previous:";
    private static final String CERTIFICATE_IDS_KEY = "certificate:ids";
    private static final double SURGE_FACTOR_INCREASE = 0.2;

    @Override
    public void logKeyword(SearchLog searchLog) {
        String keyword = normalizeKeyword(searchLog.keyword());
        if (keyword.isEmpty()) {
            return;
        }

        String certificateId = searchLog.certificateId().value().toString();
        String realtimeKey = buildRealtimeKey(searchLog.certificateId());
        String keywordCountKey = buildKeywordCountKey(searchLog.certificateId(), keyword);

        updateKewordCount(keywordCountKey);
        updateKeywordScore(realtimeKey, keyword, keywordCountKey);
        registerCertificateId(certificateId);
        trimExcessKeywords(realtimeKey);
    }

    @Override
    public TrendingKeywordsRanking getTrendingKeywordRanking(CertificateId certificateId) {
        String realtimeKey = buildRealtimeKey(certificateId);
        String previousKey = buildPreviousKey(certificateId);

        Set<TypedTuple<String>> currentKeywords = getCurrentKeywords(realtimeKey);
        if (currentKeywords.isEmpty()) {
            return TrendingKeywordsRanking.from(Collections.emptyMap());
        }

        Map<String, Integer> previousRankMap = getPreviousRankMap(previousKey);
        return buildTrendingKeywordsRanking(currentKeywords, previousRankMap);
    }

    @Scheduled(fixedRate = RECENT_WINDOW_SECONDS * 1000)
    @SchedulerLock(name = "trending_decay", lockAtMostFor = "PT2S", lockAtLeastFor = "PT2S")
    public void decayTrendingScores() {
        Set<String> certificateIds = getCertificateIds();
        if (certificateIds.isEmpty()) {
            return;
        }

        for (String certificateId : certificateIds) {
            decayScoresForCertificate(CertificateId.from(certificateId));
        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    @SchedulerLock(name = "trending_ranking", lockAtMostFor = "PT2S", lockAtLeastFor = "PT2S")
    public void savePreviousRanking() {
        Set<String> certificateIds = getCertificateIds();
        if (certificateIds.isEmpty()) {
            return;
        }

        for (String certificateId : certificateIds) {
            saveRankingForCertificate(certificateId);
        }
    }

    // 키워드 정규화
    private String normalizeKeyword(String keyword) {
        return keyword == null ? "" : keyword.toLowerCase().trim();
    }

    // 키 생성 메서드들
    private String buildRealtimeKey(CertificateId certificateId) {
        return REALTIME_KEY_PREFIX + certificateId.value();
    }

    private String buildKeywordCountKey(CertificateId certificateId, String keyword) {
        return COUNT_KEY_PREFIX + certificateId.value() + ":" + keyword;
    }

    private String buildPreviousKey(CertificateId certificateId) {
        return PREVIOUS_KEY_PREFIX + certificateId.value();
    }

    // 검색어 카운트 업데이트
    private void updateKewordCount(String queryCountKey) {
        redisTemplate.opsForValue().increment(queryCountKey, 1);
        redisTemplate.expire(queryCountKey, RECENT_WINDOW_SECONDS, TimeUnit.SECONDS);
    }

    // 키워드 점수 업데이트
    private void updateKeywordScore(String realtimeKey, String keyword, String queryCountKey) {
        long recentCount = getRecentCount(queryCountKey);
        double score = calculateScore(recentCount);
        redisTemplate.opsForZSet().incrementScore(realtimeKey, keyword, score);
    }

    // 최근 검색 횟수 조회
    private long getRecentCount(String queryCountKey) {
        String countValue = redisTemplate.opsForValue().get(queryCountKey);
        return countValue != null ? Long.parseLong(countValue) : 1L;
    }

    // 점수 계산
    private double calculateScore(long recentCount) {
        return 1.0 + (recentCount - 1) * SURGE_FACTOR_INCREASE;
    }

    // 자격증 ID 등록
    private void registerCertificateId(String certificateId) {
        redisTemplate.opsForSet().add(CERTIFICATE_IDS_KEY, certificateId);
    }

    // 초과 키워드 정리
    private void trimExcessKeywords(String key) {
        Long size = redisTemplate.opsForZSet().size(key);
        if (size != null && size > TRENDING_LIMIT) {
            redisTemplate.opsForZSet().removeRange(key, 0, -TRENDING_LIMIT - 1);
        }
    }

    // 현재 키워드 조회
    private Set<TypedTuple<String>> getCurrentKeywords(String realtimeKey) {
        Set<TypedTuple<String>> keywords =
                redisTemplate.opsForZSet().reverseRangeWithScores(realtimeKey, 0, TRENDING_LIMIT - 1);
        return keywords != null ? keywords : Collections.emptySet();
    }

    // 이전 순위 맵 생성
    private Map<String, Integer> getPreviousRankMap(String previousKey) {
        Set<TypedTuple<String>> previousKeywords =
                redisTemplate.opsForZSet().reverseRangeWithScores(previousKey, 0, -1);

        Map<String, Integer> previousRankMap = new HashMap<>();
        if (previousKeywords != null && !previousKeywords.isEmpty()) {
            int rank = 1;
            for (TypedTuple<String> tuple : previousKeywords) {
                previousRankMap.put(tuple.getValue(), rank++);
            }
        }
        return previousRankMap;
    }

    // 트렌딩 키워드 랭킹 생성
    private TrendingKeywordsRanking buildTrendingKeywordsRanking(
            Set<TypedTuple<String>> currentKeywords,
            Map<String, Integer> previousRankMap) {

        Map<String, TrendingKeyword> keywordMap = new HashMap<>();
        int rank = 1;

        for (TypedTuple<String> tuple : currentKeywords) {
            String keyword = tuple.getValue();
            KeywordStatus status = determineKeywordStatus(keyword, rank, previousRankMap);
            keywordMap.put(keyword, new TrendingKeyword(rank, keyword, status));
            rank++;
        }

        return TrendingKeywordsRanking.from(keywordMap);
    }

    // 키워드 상태 결정
    private KeywordStatus determineKeywordStatus(String keyword, int currentRank, Map<String, Integer> previousRankMap) {
        if (!previousRankMap.containsKey(keyword)) {
            return KeywordStatus.NEW;
        }

        int previousRank = previousRankMap.get(keyword);
        if (currentRank < previousRank) {
            return KeywordStatus.RANK_UP;
        } else if (currentRank > previousRank) {
            return KeywordStatus.RANK_DOWN;
        } else {
            return KeywordStatus.UNCHANGED;
        }
    }

    // 자격증 ID 목록 조회
    private Set<String> getCertificateIds() {
        Set<String> certificateIds = redisTemplate.opsForSet().members(CERTIFICATE_IDS_KEY);
        return certificateIds != null ? certificateIds : Collections.emptySet();
    }

    // 특정 자격증의 키워드 점수 감소
    private void decayScoresForCertificate(CertificateId certificateId) {
        String realtimeKey = buildRealtimeKey(certificateId);
        Set<ZSetOperations.TypedTuple<String>> allTerms =
                redisTemplate.opsForZSet().rangeWithScores(realtimeKey, 0, -1);

        if (allTerms == null || allTerms.isEmpty()) {
            return;
        }

        for (ZSetOperations.TypedTuple<String> item : allTerms) {
            updateDecayedScore(realtimeKey, item);
        }
    }

    // 감소된 점수 업데이트
    private void updateDecayedScore(String realtimeKey, ZSetOperations.TypedTuple<String> item) {
        String term = item.getValue();
        Double currentScore = item.getScore();

        if (currentScore == null) {
            return;
        }

        double newScore = currentScore * DECAY_FACTOR;
        if (newScore < MIN_SCORE) {
            redisTemplate.opsForZSet().remove(realtimeKey, term);
        } else {
            redisTemplate.opsForZSet().add(realtimeKey, term, newScore);
        }
    }

    // 특정 자격증의 이전 랭킹 저장
    private void saveRankingForCertificate(String certificateId) {
        String realtimeKey = REALTIME_KEY_PREFIX + certificateId;
        String previousKey = PREVIOUS_KEY_PREFIX + certificateId;

        Set<TypedTuple<String>> currentRanking =
                redisTemplate.opsForZSet().reverseRangeWithScores(realtimeKey, 0, TRENDING_LIMIT - 1);

        if (currentRanking == null || currentRanking.isEmpty()) {
            return;
        }

        // 이전 순위 정보 초기화 및 저장
        redisTemplate.delete(previousKey);
        for (TypedTuple<String> item : currentRanking) {
            redisTemplate.opsForZSet().add(previousKey, item.getValue(), item.getScore());
        }
    }
}
