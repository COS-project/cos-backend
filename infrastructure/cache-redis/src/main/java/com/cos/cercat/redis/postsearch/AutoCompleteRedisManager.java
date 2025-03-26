package com.cos.cercat.redis.postsearch;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.searchlog.AutoCompleteManager;
import com.cos.cercat.domain.searchlog.SearchLog;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoCompleteRedisManager implements AutoCompleteManager {

    private final static String TERMS = "terms";
    private final static String CHOSUNG = "chosung";
    private final static String PREFIX = "prefix";

    private final StringRedisTemplate redisTemplate;

    @Override
    public void indexForAutoComplete(SearchLog searchLog) {
        String term = searchLog.keyword().toLowerCase().trim();
        CertificateId certificateId = searchLog.certificateId();

        // 1. 전체 검색어 인덱스에 추가/점수 증가
        redisTemplate.opsForZSet().incrementScore(getKey(certificateId, TERMS), term, 1);

        // 2. 접두어 인덱싱
        for (int i = 1; i <= term.length(); i++) {
            String prefix = term.substring(0, i);
            redisTemplate.opsForSet().add(getKey(certificateId, PREFIX) + ":" + prefix, term);
        }

        String chosungKey = getKey(certificateId, CHOSUNG);
        // 3. 한글 초성 추출 및 인덱싱
        String chosung = extractChosung(term);
        if (!chosung.isEmpty()) {
            redisTemplate.opsForSet().add(chosungKey + ":" + chosung, term);

            // 초성 부분 매칭을 위한 인덱싱
            for (int i = 1; i <= chosung.length(); i++) {
                String chosungPrefix = chosung.substring(0, i);
                redisTemplate.opsForSet().add(chosungKey + ":" + chosungPrefix, term);
            }
        }
    }

    @Override
    public List<String> getAutoCompleteSuggestions(Certificate certificate, String query, int limit) {
        query = query.toLowerCase().trim();

        // 1. 정확한 접두어 매칭
        Set<String> prefixMatches = redisTemplate.opsForSet().members(getKey(certificate.id(), PREFIX) + ":" + query);

        String s = getKey(certificate.id(), CHOSUNG) + ":" + query;
        // 2. 초성 매칭
        Set<String> chosungMatches = redisTemplate.opsForSet().members(s);

        // 3. 결과 합치기
        Set<String> allMatches = new HashSet<>();
        if (prefixMatches != null) allMatches.addAll(prefixMatches);
        if (chosungMatches != null) allMatches.addAll(chosungMatches);

        String termsKey = getKey(certificate.id(), TERMS);

        // 4. 인기도 순으로 정렬
        return allMatches.stream()
                .sorted((a, b) -> {
                    Double scoreA = redisTemplate.opsForZSet().score(termsKey, a);
                    Double scoreB = redisTemplate.opsForZSet().score(termsKey, b);
                    scoreA = scoreA == null ? 0 : scoreA;
                    scoreB = scoreB == null ? 0 : scoreB;
                    return Double.compare(scoreB, scoreA);
                })
                .limit(limit)
                .collect(Collectors.toList());
    }

    private String extractChosung(String text) {
        final char[] COMPAT_CHOSUNG = {
                'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
                'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
        };

        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (isHangul(c)) {
                int chosungIndex = (c - 0xAC00) / (21 * 28);

                if (chosungIndex >= 0 && chosungIndex < COMPAT_CHOSUNG.length) {
                    result.append(COMPAT_CHOSUNG[chosungIndex]);
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    private boolean isHangul(char c) {
        return c >= 0xAC00 && c <= 0xD7A3;
    }

    private String getKey(CertificateId id, String keyPrefix) {
        return "autocomplete:" + id.value() + ":" + keyPrefix;
    }
}
