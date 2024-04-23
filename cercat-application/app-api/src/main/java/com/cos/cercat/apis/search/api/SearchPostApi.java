package com.cos.cercat.apis.search.api;

import com.cos.cercat.apis.post.dto.response.PostResponse;
import com.cos.cercat.apis.user.dto.request.SearchLogDeleteRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.search.SearchCond;
import com.cos.cercat.domain.search.SearchLog;
import com.cos.cercat.domain.search.SearchPostService;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cos.cercat.apis.global.util.CursorConvertor.toCursor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "통합 검색 API")
public class SearchPostApi {

    private final SearchPostService searchPostService;

    @GetMapping("/certificates/{certificateId}/search")
    @Operation(summary = "통합 검색(모든 게시글, 댓글)", description = "통합 검색엔진")
    public Response<SliceResult<PostResponse>> search(SearchCond cond,
                                                      @AuthenticationPrincipal UserDTO user,
                                                      @PathVariable Long certificateId,
                                                      Pageable pageable) {
        SliceResult<Post> posts = searchPostService.search(TargetUser.from(user.getId()), TargetCertificate.from(certificateId), cond, toCursor(pageable));

        return Response.success(posts.map(PostResponse::from));
    }

    @GetMapping("/users/search-logs")
    @Operation(summary = "최근 검색 기록 조회")
    public Response<List<SearchLog>> getSearchLogs(@AuthenticationPrincipal UserDTO user) {
        return Response.success(searchPostService.readSearchLogs(TargetUser.from(user.getId())));
    }

    @GetMapping("/certificates/{certificateId}/auto-complete-keywords")
    @Operation(summary = "자동완성 검색어 조회")
    public Response<List<String>> getAutoCompleteKeywords(@RequestParam String searchText,
                                                          @PathVariable Long certificateId) {
        return Response.success(searchPostService.readAutoCompleteKeyword(TargetCertificate.from(certificateId), searchText));
    }

    @GetMapping("/certificates/{certificateId}/recent-top5-keywords")
    @Operation(summary = "실시간 검색어 TOP5 조회")
    public Response<List<String>> getRecentTop5Keywords(@PathVariable Long certificateId) {
        return Response.success(searchPostService.getRecentTop5Keywords(TargetCertificate.from(certificateId)));
    }


    @DeleteMapping("/search-logs")
    @Operation(summary = "특정 검색 기록 삭제")
    public Response<Void> deleteSearchLogs(@AuthenticationPrincipal UserDTO currentUser,
                                           SearchLogDeleteRequest request) {
        searchPostService.deleteSearchLog(TargetUser.from(currentUser.getId()), request.toSearchLog());
        return Response.success("검색 기록 삭제 성공");
    }

    @DeleteMapping("/search-logs/all")
    @Operation(summary = "모든 검색 기록 삭제")
    public Response<Void> deleteAllSearchLogs(@AuthenticationPrincipal UserDTO currentUser) {
        searchPostService.deleteAllSearchLogs(TargetUser.from(currentUser.getId()));
        return Response.success("모든 검색 기록 삭제 성공");
    }
}
