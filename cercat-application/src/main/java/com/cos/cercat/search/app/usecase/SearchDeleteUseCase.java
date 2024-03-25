package com.cos.cercat.search.app.usecase;

import com.cos.cercat.annotation.UseCase;
import com.cos.cercat.search.service.SearchLogService;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.dto.request.SearchLogDeleteRequest;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SearchDeleteUseCase {

    private final SearchLogService searchLogService;

    public void deleteSearchLog(UserDTO userDTO, SearchLogDeleteRequest request) {
        searchLogService.deleteSearchLog(userDTO, request.keyword(), request.createdAt());
    }

    public void deleteAllSearchLogs(UserDTO userDTO) {
        searchLogService.deleteAllSearchLogs(userDTO);
    }

}
