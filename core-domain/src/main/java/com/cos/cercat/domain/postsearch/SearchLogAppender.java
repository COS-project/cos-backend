package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SearchLogAppender {

    private final SearchLogCache searchLogCache;

    public void append(User user, SearchLog searchLog) {
        if (searchLog.isNotValid()) {
            return;
        }
        searchLogCache.cache(user, searchLog);
    }

}
