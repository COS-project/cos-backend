package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SearchLogAppender {

    private final SearchLogCache searchLogCache;

    public void append(User user, String searchKeyword) {
        if (searchKeyword != null && !searchKeyword.isBlank()) {
            SearchLog searchLog = new SearchLog(searchKeyword, LocalDateTime.now().toString());
            searchLogCache.cache(user, searchLog);
        }
    }

}
