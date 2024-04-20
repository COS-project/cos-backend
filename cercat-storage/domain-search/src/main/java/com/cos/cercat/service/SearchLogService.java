package com.cos.cercat.service;

import com.cos.cercat.cache.SearchLog;
import com.cos.cercat.cache.SearchLogCacheRepository;
import com.cos.cercat.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchLogService {

    private final SearchLogCacheRepository searchLogCacheRepository;

    public void saveSearchLog(UserDTO userDTO, String searchKeyword) {
        SearchLog searchLog = SearchLog.builder()
                .keyword(searchKeyword)
                .createdAt(LocalDateTime.now().toString())
                .build();
        searchLogCacheRepository.setLog(userDTO.getEmail(), searchLog);
    }

    public List<SearchLog> getSearchLogs(UserDTO currentUser) {
        return searchLogCacheRepository.getSearchLogs(currentUser.getEmail());
    }

    public void deleteSearchLog(UserDTO currentUser, String keyword, String createdAt) {
        SearchLog searchLog = SearchLog.builder()
                .keyword(keyword)
                .createdAt(createdAt)
                .build();
        searchLogCacheRepository.deleteLog(currentUser.getEmail(), searchLog);
    }

    public void deleteAllSearchLogs(UserDTO currentUser) {
        searchLogCacheRepository.deleteAllLogs(currentUser.getEmail());
    }

}
