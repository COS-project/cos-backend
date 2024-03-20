package com.cos.cercat.post.api;

import com.cos.cercat.post.app.facade.PostFetchService;
import com.cos.cercat.post.app.search.dto.SearchCond;
import com.cos.cercat.post.domain.PostType;
import com.cos.cercat.post.dto.request.CommentaryPostSearchCond;
import com.cos.cercat.post.dto.response.PostResponse;
import com.cos.cercat.post.dto.response.PostWithCommentsResponse;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시글 조회 API")
public class PostFetchApi {

    private final PostFetchService postFetchService;

    @GetMapping("/certificates/{certificateId}/search")
    @Operation(summary = "통합 검색(모든 게시글, 댓글)", description = "통합 검색엔진")
    public Response<Slice<PostResponse>> search(SearchCond cond,
                                                @AuthenticationPrincipal UserDTO user,
                                                @PathVariable Long certificateId,
                                                Pageable pageable) {
        return Response.success(postFetchService.search(cond, user, certificateId, pageable));
    }

    @GetMapping("/certificates/{certificateId}/posts")
    @Operation(summary = "해설 게시글 검색")
    public Response<Slice<PostResponse>> searchCommentaryPosts(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                               @PathVariable Long certificateId,
                                                               CommentaryPostSearchCond cond,
                                                               @AuthenticationPrincipal UserDTO user) {
        return Response.success(postFetchService.searchCommentaryPosts(pageable, certificateId, cond, user.getId()));
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public Response<PostWithCommentsResponse> getPostDetail(@PathVariable Long postId,
                                                            @AuthenticationPrincipal UserDTO user) {
        return Response.success(postFetchService.getPostDetail(postId, user.getId()));
    }

    @GetMapping("/certificates/{certificateId}/tip-posts/best")
    @Operation(summary = "베스트 꿀팁 TOP3 조회")
    public Response<List<PostResponse>> getTop3TipPosts(@PathVariable Long certificateId,
                                                        @AuthenticationPrincipal UserDTO user) {
        return Response.success(postFetchService.getTop3TipPosts(certificateId, user.getId()));
    }

    @GetMapping("/{postType}/posts/my-posts")
    @Operation(summary = "내가 쓴 글 조회")
    public Response<Slice<PostResponse>> getMyPosts(@PathVariable PostType postType,
                                                   @AuthenticationPrincipal UserDTO user,
                                                   @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(postFetchService.getMyPosts(postType, user.getId(), pageable));
    }

    @GetMapping("/comment-posts/my-comment-posts")
    @Operation(summary = "내가 댓글 쓴 게시글 조회")
    public Response<Slice<PostResponse>> getMyCommentPosts(@AuthenticationPrincipal UserDTO user,
                                                          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(postFetchService.getMyCommentPosts(user.getId(), pageable));
    }
}
