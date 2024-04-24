package com.cos.cercat.apis.search.api;

import com.cos.cercat.apis.post.response.PostResponse;
import com.cos.cercat.apis.user.dto.request.SearchLogDeleteRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.search.SearchCond;
import com.cos.cercat.domain.search.SearchLog;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Tag(name = "통합검색 API")
public interface SearchPostApiDocs {

    @Operation(summary = "통합 검색(모든 게시글, 댓글)", description = "통합 검색엔진")
    Response<SliceResult<PostResponse>> search(SearchCond cond,
                                                      UserDTO user,
                                                      Long certificateId,
                                                      Pageable pageable);

    @Operation(summary = "최근 검색 기록 조회")
    Response<List<SearchLog>> getSearchLogs(UserDTO user);

    @Operation(summary = "자동완성 검색어 조회")
    Response<List<String>> getAutoCompleteKeywords(String searchText,
                                                   Long certificateId);

    @Operation(summary = "실시간 검색어 TOP5 조회")
    Response<List<String>> getRecentTop5Keywords(Long certificateId);

    @Operation(summary = "특정 검색 기록 삭제")
    Response<Void> deleteSearchLogs(UserDTO currentUser,
                                    SearchLogDeleteRequest request);

    @Operation(summary = "모든 검색 기록 삭제")
    Response<Void> deleteAllSearchLogs(UserDTO currentUser);
}
