package com.cos.cercat.apis.search.api;

import com.cos.cercat.apis.search.app.usecase.SearchDeleteUseCase;
import com.cos.cercat.common.dto.Response;
import com.cos.cercat.domain.user.dto.UserDTO;
import com.cos.cercat.apis.user.dto.request.SearchLogDeleteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "검색 정보 삭제 API")
public class SearchDeleteApi {

    private final SearchDeleteUseCase searchDeleteUseCase;

    @DeleteMapping("/search-logs")
    @Operation(summary = "특정 검색 기록 삭제")
    public Response<Void> deleteSearchLogs(@AuthenticationPrincipal UserDTO user,
                                           @RequestParam SearchLogDeleteRequest request) {
        searchDeleteUseCase.deleteSearchLog(user, request);
        return Response.success("검색 기록 삭제 성공");
    }

    @DeleteMapping("/search-logs/all")
    @Operation(summary = "모든 검색 기록 삭제")
    public Response<Void> deleteAllSearchLogs(@AuthenticationPrincipal UserDTO user) {
        searchDeleteUseCase.deleteAllSearchLogs(user);
        return Response.success("모든 검색 기록 삭제 성공");
    }

}
