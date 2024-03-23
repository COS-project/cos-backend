package com.cos.cercat.search.service;

import com.cos.cercat.search.cache.SearchLog;
import com.cos.cercat.search.cache.SearchLogCacheRepository;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.dto.request.SearchLogDeleteRequest;
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

    public List<SearchLog> getSearchLogs(UserDTO userDTO) {
        return searchLogCacheRepository.getSearchLogs(userDTO.getEmail());
    }

    public void deleteSearchLog(UserDTO userDTO, SearchLogDeleteRequest request) {
        SearchLog searchLog = SearchLog.builder()
                .keyword(request.keyword())
                .createdAt(request.createdAt())
                .build();
        searchLogCacheRepository.deleteLog(userDTO.getEmail(), searchLog);
    }

    public void deleteAllSearchLogs(UserDTO userDTO) {
        searchLogCacheRepository.deleteAllLogs(userDTO.getEmail());
    }

}
