package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.certificate.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrendingKeywordsCacheManager {

    private final TrendingKeywordRepository trendingKeywordRepository;

    public void setTrendingKeywords(Certificate certificate, List<TrendingKeyword> trendingKeywords) {
        trendingKeywordRepository.setTrendingKeywords(certificate, trendingKeywords);
    }

    public List<TrendingKeyword> findTrendingKeywords(Certificate certificate) {
        return trendingKeywordRepository.findTrendingKeywords(certificate);
    }

}
