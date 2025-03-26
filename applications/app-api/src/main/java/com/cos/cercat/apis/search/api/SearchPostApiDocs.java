package com.cos.cercat.apis.search.api;

import com.cos.cercat.apis.post.response.PostResponse;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.searchlog.SearchCond;
import com.cos.cercat.domain.searchlog.TrendingKeyword;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "통합검색 API")
public interface SearchPostApiDocs {

    @Operation(summary = "최근 검색 기록 조회")
    Response<List<String>> getSearchLogs(User user, Long certificateId);

    @Operation(summary = "자동완성 검색어 조회")
    Response<List<String>> getAutoCompleteKeywords(String searchText, Long certificateId);

    @Operation(summary = "실시간 검색어 TOP10 조회")
    Response<List<TrendingKeyword>> getTrendingKeywords(Long certificateId);

    @Operation(summary = "특정 검색 기록 삭제")
    Response<Void> deleteSearchLogs(User currentUser, Long certificateId, String keyword);

    @Operation(summary = "모든 검색 기록 삭제")
    Response<Void> deleteAllSearchLogs(User currentUser, Long certificateId);
}
