package com.cos.cercat.search;

import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchLogRemover {

    private final SearchLogRepository searchLogRepository;


    public void deleteSearchLog(User user, SearchLog searchLog) {
        searchLogRepository.deleteSearchLog(user, searchLog);
    }

    public void deleteAllSearchLogs(User user) {
        searchLogRepository.deleteAllSearchLogs(user);
    }
}
