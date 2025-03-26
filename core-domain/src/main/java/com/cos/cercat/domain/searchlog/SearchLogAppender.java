package com.cos.cercat.domain.searchlog;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchLogAppender {

    private final UserSearchLogQueue userSearchLogQueue;
    private final AutoCompleteManager autoCompleteManager;
    private final TrendingKeywordManager trendingKeywordManager;

    public void append(User user, SearchLog searchLog) {
        if (searchLog.notValid()) {
            return;
        }

        if (userSearchLogQueue.exists(user, searchLog)) {
            userSearchLogQueue.pop(user, searchLog);
        }

        userSearchLogQueue.push(user, searchLog);
        autoCompleteManager.indexForAutoComplete(searchLog);
        trendingKeywordManager.logKeyword(searchLog);
    }

}
