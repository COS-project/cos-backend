package com.cos.cercat.domain.searchlog;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchLogService {

    private final CertificateReader certificateReader;
    private final UserReader userReader;
    private final SearchLogRemover searchLogRemover;
    private final SearchLogReader searchLogReader;

    public List<String> readAutoCompleteKeyword(CertificateId certificateId, String query) {
        Certificate certificate = certificateReader.read(certificateId);
        return searchLogReader.readAutoCompletedKeywords(certificate, query);
    }

    public TrendingKeywordsRanking getTrendingKeywords(CertificateId certificateId) {
        Certificate certificate = certificateReader.read(certificateId);
        return searchLogReader.readTrendingKeywordsRanking(certificate);
    }

    public List<String> readUserSearchHistories(UserId userId, CertificateId certificateId) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        return searchLogReader.readUserSearchHistories(user, certificate);
    }

    public void deleteUserSearchHistory(UserId userId, SearchLog searchLog) {
        User user = userReader.read(userId);
        searchLogRemover.deleteUserSearchHistory(user, searchLog);
    }

    public void deleteAllUserSearchHistories(UserId userId, CertificateId certificateId) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        searchLogRemover.deleteAllUserSearchHistories(user, certificate);
    }
    
    
}
