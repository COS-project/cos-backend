package com.cos.cercat.search;

import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SearchLogAppender {

    private final SearchLogRepository searchLogRepository;

    public void appendSearchLog(User user, SearchCond cond) {
        if (!cond.keyword().isBlank()) {
            SearchLog searchLog = new SearchLog(cond.keyword(), LocalDateTime.now().toString());
            searchLogRepository.setLog(user, searchLog);
        }
    }

}
