package com.cos.cercat.apis.search.api;

import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.searchlog.SearchLog;
import com.cos.cercat.domain.searchlog.SearchLogService;
import com.cos.cercat.domain.searchlog.TrendingKeyword;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class SearchLogApi implements SearchLogApiDocs {

    private final SearchLogService searchLogService;

    @GetMapping("/certificates/{certificateId}/search-logs")
    public Response<List<String>> getSearchLogs(
            @AuthenticationPrincipal User user,
            @PathVariable Long certificateId
    ) {
        List<String> searchLogs = searchLogService.readUserSearchHistories(
                UserId.from(user.getId()),
                CertificateId.from(certificateId)
        );
        return Response.success(searchLogs);
    }

    @GetMapping("/certificates/{certificateId}/auto-complete-keywords")
    public Response<List<String>> getAutoCompleteKeywords(@RequestParam String query,
                                                          @PathVariable Long certificateId) {
        return Response.success(searchLogService.readAutoCompleteKeyword(CertificateId.from(certificateId),
                query));
    }

    @GetMapping("/certificates/{certificateId}/trending-keywords")
    public Response<List<TrendingKeyword>> getTrendingKeywords(@PathVariable Long certificateId) {
        return Response.success(
                searchLogService.getTrendingKeywords(CertificateId.from(certificateId)).getKeywordList());
    }


    @DeleteMapping("/certificates/{certificateId}/search-logs")
    public Response<Void> deleteSearchLogs(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long certificateId,
            String keyword
    ) {
        searchLogService.deleteUserSearchHistory(
                UserId.from(currentUser.getId()),
                SearchLog.of(certificateId, keyword)
        );
        return Response.success("검색 기록 삭제 성공");
    }

    @DeleteMapping("/certificates/{certificateId}/search-logs/all")
    public Response<Void> deleteAllSearchLogs(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long certificateId
    ) {
        searchLogService.deleteAllUserSearchHistories(
                UserId.from(currentUser.getId()),
                CertificateId.from(certificateId)
        );
        return Response.success("모든 검색 기록 삭제 성공");
    }
}
