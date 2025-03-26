package com.cos.cercat.domain.searchlog;

import com.cos.cercat.domain.certificate.CertificateId;

public interface TrendingKeywordManager {

    void logKeyword(SearchLog searchLog);

    TrendingKeywordsRanking getTrendingKeywordRanking(CertificateId certificateId);
}
