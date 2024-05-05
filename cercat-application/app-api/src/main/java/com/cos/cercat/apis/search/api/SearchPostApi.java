package com.cos.cercat.apis.search.api;

import com.cos.cercat.apis.post.response.PostResponse;
import com.cos.cercat.apis.user.request.SearchLogDeleteRequest;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.post.Post;
import com.cos.cercat.search.SearchCond;
import com.cos.cercat.search.SearchLog;
import com.cos.cercat.search.SearchPostService;
import com.cos.cercat.search.TrendingKeyword;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class SearchPostApi implements SearchPostApiDocs {

    private final SearchPostService searchPostService;

    @GetMapping("/certificates/{certificateId}/search")
    public Response<SliceResult<PostResponse>> search(SearchCond cond,
                                                      @AuthenticationPrincipal User user,
                                                      @PathVariable Long certificateId,
                                                      Cursor cursor) {
        SliceResult<Post> posts = searchPostService.search(TargetUser.from(user.getId()), TargetCertificate.from(certificateId), cond, cursor);
        return Response.success(posts.map(PostResponse::from));
    }

    @GetMapping("/users/search-logs")
    public Response<List<SearchLog>> getSearchLogs(@AuthenticationPrincipal User user) {
        return Response.success(searchPostService.readSearchLogs(TargetUser.from(user.getId())));
    }

    @GetMapping("/certificates/{certificateId}/auto-complete-keywords")
    public Response<List<String>> getAutoCompleteKeywords(@RequestParam String searchText,
                                                          @PathVariable Long certificateId) {
        return Response.success(searchPostService.readAutoCompleteKeyword(TargetCertificate.from(certificateId), searchText));
    }

    @GetMapping("/certificates/{certificateId}/trending-keywords")
    public Response<List<TrendingKeyword>> getTrendingKeywords(@PathVariable Long certificateId) {
        return Response.success(searchPostService.getTrendingKeywords(TargetCertificate.from(certificateId)));
    }


    @DeleteMapping("/search-logs")
    public Response<Void> deleteSearchLogs(@AuthenticationPrincipal User currentUser,
                                           SearchLogDeleteRequest request) {
        searchPostService.deleteSearchLog(TargetUser.from(currentUser.getId()), request.toSearchLog());
        return Response.success("검색 기록 삭제 성공");
    }

    @DeleteMapping("/search-logs/all")
    public Response<Void> deleteAllSearchLogs(@AuthenticationPrincipal User currentUser) {
        searchPostService.deleteAllSearchLogs(TargetUser.from(currentUser.getId()));
        return Response.success("모든 검색 기록 삭제 성공");
    }
}
