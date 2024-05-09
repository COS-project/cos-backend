package com.cos.cercat.apis.search.api;

import com.cos.cercat.apis.post.response.PostResponse;
import com.cos.cercat.apis.user.request.SearchLogDeleteRequest;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.search.SearchCond;
import com.cos.cercat.search.SearchLog;
import com.cos.cercat.search.TrendingKeyword;
import com.cos.cercat.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "통합검색 API")
public interface SearchPostApiDocs {

    @Operation(summary = "통합 검색(모든 게시글, 댓글)", description = "통합 검색엔진")
    Response<SliceResult<PostResponse>> search(SearchCond cond,
                                               User user,
                                               Long certificateId,
                                               @Parameter(examples = {
                                                       @ExampleObject(name = "cursor", value = """ 
                                                                    {
                                                                        "page" : 0,
                                                                        "size" : 10,
                                                                        "sortFields" : "createdAt, id",
                                                                        "sortDirections" : "DESC, ASC"
                                                                    }
                                                                """)
                                               })
                                               Cursor cursor);

    @Operation(summary = "최근 검색 기록 조회")
    Response<List<SearchLog>> getSearchLogs(User user);

    @Operation(summary = "자동완성 검색어 조회")
    Response<List<String>> getAutoCompleteKeywords(String searchText,
                                                   Long certificateId);

    @Operation(summary = "실시간 검색어 TOP10 조회")
    Response<List<TrendingKeyword>> getTrendingKeywords(Long certificateId);

    @Operation(summary = "특정 검색 기록 삭제")
    Response<Void> deleteSearchLogs(User currentUser,
                                    SearchLogDeleteRequest request);

    @Operation(summary = "모든 검색 기록 삭제")
    Response<Void> deleteAllSearchLogs(User currentUser);
}
