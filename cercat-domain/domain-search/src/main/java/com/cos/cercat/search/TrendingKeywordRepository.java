package com.cos.cercat.search;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.TargetCertificate;

import java.util.List;
import java.util.Optional;

public interface TrendingKeywordRepository {
    void setTrendingKeywords(Certificate certificate, List<TrendingKeyword> trendingKeywords);

    List<TrendingKeyword> findTrendingKeywords(Certificate certificate);
}
