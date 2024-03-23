package com.cos.cercat.search.service.facade;

import com.cos.cercat.search.service.SearchLogService;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.dto.request.SearchLogDeleteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchDeleteService {

    private final SearchLogService searchLogService;

    public void deleteSearchLog(UserDTO userDTO, SearchLogDeleteRequest request) {
        searchLogService.deleteSearchLog(userDTO, request);
    }

    public void deleteAllSearchLogs(UserDTO userDTO) {
        searchLogService.deleteAllSearchLogs(userDTO);
    }

}
