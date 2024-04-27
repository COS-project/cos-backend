package com.cos.cercat.search;

import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SearchLogAppender {

    private final SearchLogRepository searchLogRepository;

    public void appendSearchLog(TargetUser targetUser, String searchKeyword) {
        SearchLog searchLog = new SearchLog(searchKeyword, LocalDateTime.now().toString());
        searchLogRepository.setLog(targetUser, searchLog);
    }

}
