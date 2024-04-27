package com.cos.cercat.search;

import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchLogReader {

    private final SearchLogRepository searchLogRepository;

    public List<SearchLog> readSearchLogs(TargetUser targetUser) {
        return searchLogRepository.findSearchLogs(targetUser);
    }

}
