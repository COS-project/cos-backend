package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchLogRemover {

    private final SearchLogCache searchLogCache;


    public void deleteSearchLog(User user, SearchLog searchLog) {
        searchLogCache.delete(user, searchLog);
    }

    public void deleteAllSearchLogs(User user) {
        searchLogCache.deleteAll(user);
    }
}
