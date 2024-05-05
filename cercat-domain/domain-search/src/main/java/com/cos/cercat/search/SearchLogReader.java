package com.cos.cercat.search;

import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchLogReader {

    private final SearchLogRepository searchLogRepository;

    public List<SearchLog> readSearchLogs(User user) {
        return searchLogRepository.findSearchLogs(user);
    }

}
