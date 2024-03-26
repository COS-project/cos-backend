package com.cos.cercat.apis.search.api;

import com.cos.cercat.apis.search.app.usecase.SearchFetchUseCase;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.apis.post.dto.response.PostResponse;
import com.cos.cercat.cache.SearchLog;
import com.cos.cercat.dto.SearchCond;
import com.cos.cercat.dto.UserDTO;;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "검색 정보 조회 API")
public class SearchFetchApi {

    private final SearchFetchUseCase searchFetchUseCase;

    @GetMapping("/certificates/{certificateId}/search")
    @Operation(summary = "통합 검색(모든 게시글, 댓글)", description = "통합 검색엔진")
    public Response<Slice<PostResponse>> search(SearchCond cond,
                                                @AuthenticationPrincipal UserDTO user,
                                                @PathVariable Long certificateId,
                                                Pageable pageable) {
        return Response.success(searchFetchUseCase.search(cond, user, certificateId, pageable));
    }

    @GetMapping("/users/search-logs")
    @Operation(summary = "최근 검색 기록 조회")
    public Response<List<SearchLog>> getSearchLogs(@AuthenticationPrincipal UserDTO user) {
        return Response.success(searchFetchUseCase.getSearchLogs(user));
    }

    @GetMapping("/certificates/{certificateId}/auto-complete-keywords")
    @Operation(summary = "자동완성 검색어 조회")
    public Response<List<String>> getAutoCompleteKeywords(@RequestParam String searchText,
                                                          @PathVariable Long certificateId) {
        return Response.success(searchFetchUseCase.getAutoCompleteKeywords(certificateId, searchText));
    }

    @GetMapping("/certificates/{certificateId}/recent-top5-keywords")
    @Operation(summary = "실시간 검색어 TOP5 조회")
    public Response<List<String>> getRecentTop5Keywords(@PathVariable Long certificateId) {
        return Response.success(searchFetchUseCase.getRecentTop5Keywords(certificateId));
    }

}
