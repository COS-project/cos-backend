package com.cos.cercat.domain.searchlog;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchLogReader {

    private final UserSearchLogQueue userSearchLogQueue;
    private final TrendingKeywordManager trendingKeywordManager;
    private final AutoCompleteManager autoCompleteManager;

    private static final int AUTO_COMPLETE_LIMIT = 5;

    public List<String> readUserSearchHistories(User user, Certificate certificate) {
        return userSearchLogQueue.getAll(user, certificate);
    }

    public List<String> readAutoCompletedKeywords(Certificate certificate, String searchText) {
        return autoCompleteManager.getAutoCompleteSuggestions(certificate, searchText, AUTO_COMPLETE_LIMIT);
    }

    public TrendingKeywordsRanking readTrendingKeywordsRanking(Certificate certificate) {
        return trendingKeywordManager.getTrendingKeywordRanking(certificate.id());
    }

}
