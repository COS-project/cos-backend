package com.cos.cercat.apis.search.app.usecase;

import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.dto.UserDTO;;
import com.cos.cercat.service.SearchLogService;
import com.cos.cercat.apis.user.dto.request.SearchLogDeleteRequest;
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
