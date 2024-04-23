package com.cos.cercat.domain.search;

import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchLogRemover {

    private final SearchLogRepository searchLogRepository;


    public void deleteSearchLog(TargetUser targetUser, SearchLog searchLog) {
        searchLogRepository.deleteSearchLog(targetUser, searchLog);
    }

    public void deleteAllSearchLogs(TargetUser targetUser) {
        searchLogRepository.deleteAllSearchLogs(targetUser);
    }
}
