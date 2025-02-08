package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchLogRemover {

    private final SearchLogQueue searchLogQueue;


    public void deleteSearchLog(User user, SearchLog searchLog) {
        searchLogQueue.pop(user, searchLog);
    }

    public void deleteAllSearchLogs(User user) {
        searchLogQueue.popAll(user);
    }
}
