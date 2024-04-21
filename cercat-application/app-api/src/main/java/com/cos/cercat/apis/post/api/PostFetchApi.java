package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.app.usecase.PostFetchUseCase;
import com.cos.cercat.apis.post.dto.response.PostResponse;
import com.cos.cercat.apis.post.dto.response.PostWithCommentsResponse;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.CommentaryPostSearchCond;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
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

    private final PostFetchUseCase postFetchUseCase;

    @GetMapping("/certificates/{certificateId}/posts")
    @Operation(summary = "해설 게시글 검색")
    public Response<Slice<PostResponse>> searchCommentaryPosts(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                               @PathVariable Long certificateId,
                                                               CommentaryPostSearchCond cond,
                                                               @AuthenticationPrincipal UserDTO currentUser) {
        return Response.success(postFetchUseCase.searchCommentaryPosts(pageable, certificateId, cond, currentUser.getId()));
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public Response<PostWithCommentsResponse> getPostDetail(@PathVariable Long postId,
                                                            @AuthenticationPrincipal UserDTO currentUser) {
        return Response.success(postFetchUseCase.getPostDetail(postId, currentUser.getId()));
    }

    @GetMapping("/certificates/{certificateId}/tip-posts/best")
    @Operation(summary = "베스트 꿀팁 TOP3 조회")
    public Response<List<PostResponse>> getTop3TipPosts(@PathVariable Long certificateId,
                                                        @AuthenticationPrincipal UserDTO currentUser) {
        return Response.success(postFetchUseCase.getTop3TipPosts(certificateId, currentUser.getId()));
    }

    @GetMapping("/{postType}/posts/my-posts")
    @Operation(summary = "내가 쓴 글 조회")
    public Response<Slice<PostResponse>> getMyPosts(@PathVariable PostType postType,
                                                    @AuthenticationPrincipal UserDTO currentUser,
                                                    @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(postFetchUseCase.getMyPosts(postType, currentUser.getId(), pageable));
    }

    @GetMapping("/comment-posts/my-comment-posts")
    @Operation(summary = "내가 댓글 쓴 게시글 조회")
    public Response<Slice<PostResponse>> getMyCommentPosts(@AuthenticationPrincipal UserDTO currentUser,
                                                           @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(postFetchUseCase.getMyCommentPosts(currentUser.getId(), pageable));
    }
}
