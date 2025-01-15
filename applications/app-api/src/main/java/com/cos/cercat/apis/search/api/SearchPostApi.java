package com.cos.cercat.apis.search.api;

import com.cos.cercat.apis.post.response.PostResponse;
import com.cos.cercat.apis.user.request.SearchLogDeleteRequest;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.apis.global.annotation.CursorDefault;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.postsearch.SearchCond;
import com.cos.cercat.domain.postsearch.SearchLog;
import com.cos.cercat.domain.postsearch.SearchPostService;
import com.cos.cercat.domain.postsearch.TrendingKeyword;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
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
                                                      @CursorDefault Cursor cursor) {
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
