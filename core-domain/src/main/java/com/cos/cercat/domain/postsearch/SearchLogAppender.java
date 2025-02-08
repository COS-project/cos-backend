package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchLogAppender {

    private final SearchLogQueue searchLogQueue;

    public void append(User user, SearchLog searchLog) {
        if (searchLog.notValid()) {
            return;
        }

        if (searchLogQueue.exists(user, searchLog)) {
            searchLogQueue.pop(user, searchLog);
        }

        searchLogQueue.push(user, searchLog);
    }

}
