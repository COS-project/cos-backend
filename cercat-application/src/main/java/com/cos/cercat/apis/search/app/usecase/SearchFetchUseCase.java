package com.cos.cercat.apis.search.app.usecase;

import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.like.service.PostLikeService;
import com.cos.cercat.domain.like.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.domain.post.service.PostService;
import com.cos.cercat.apis.post.dto.response.PostResponse;
import com.cos.cercat.domain.search.cache.SearchLog;
import com.cos.cercat.domain.search.domain.PostDocument;
import com.cos.cercat.domain.search.dto.SearchCond;
import com.cos.cercat.domain.search.service.PostSearchService;
import com.cos.cercat.domain.search.service.SearchLogService;
import com.cos.cercat.domain.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchFetchUseCase {

    private final SearchLogService searchLogService;
    private final PostSearchService postSearchService;
    private final PostService postService;
    private final PostLikeService postLikeService;

    /***
     * 댓글, 게시글을 포함해 통합 검색합니다.
     * @param cond 검색 필터
     * @param pageable 페이징 정보
     * @return Slice 형태의 게시글 Response DTO를 반환합니다.
     */
    public Slice<PostResponse> search(SearchCond cond,
                                      UserDTO user,
                                      Long certificateId,
                                      Pageable pageable) {
        Slice<PostDocument> documents = postSearchService.search(cond, certificateId, pageable);

        if (StringUtils.hasText(cond.keyword())) {
            searchLogService.saveSearchLog(user, cond.keyword());
        }

        return documents.map(PostDocument::getId)
                .map(postService::getPost)
                .map(post -> PostResponse.of(post, isLiked(user.getId(), post.getId())));
    }

    public List<SearchLog> getSearchLogs(UserDTO userDTO) {
        return searchLogService.getSearchLogs(userDTO);
    }

    public List<String> getAutoCompleteKeywords(Long certificateId, String keyword) {
        return postSearchService.getAutoCompletedKeywords(certificateId, keyword);
    }

    public List<String> getRecentTop5Keywords(Long certificateId) {
        return postSearchService.getRecentTop5Keywords(certificateId);
    }

    private boolean isLiked(Long userId, Long postId) {
        return postLikeService.existsLike(PostLikePK.of(userId, postId));
    }

}
