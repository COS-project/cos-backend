package com.cos.cercat.post.api;

import com.cos.cercat.post.app.PostFetchService;
import com.cos.cercat.post.domain.PostType;
import com.cos.cercat.post.dto.request.PostSearchCond;
import com.cos.cercat.post.dto.response.PostResponse;
import com.cos.cercat.post.dto.response.PostWithCommentsResponse;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/{certificateId}/{postType}/posts")
    @Operation(summary = "게시글 검색", description = "키워드가 안주어졌을 경우 전체 조회")
    public Response<Slice<PostResponse>> getCommentaryPosts(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                            @PathVariable Long certificateId,
                                                            @PathVariable PostType postType,
                                                            PostSearchCond cond) {

        return Response.success(postFetchService.searchPosts(pageable, postType, certificateId, cond));
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public Response<PostWithCommentsResponse> getCommentaryPostDetail(@PathVariable Long postId) {
        return Response.success(postFetchService.getPostDetail(postId));
    }

    @GetMapping("/{certificateId}/tip-posts/best")
    @Operation(summary = "베스트 꿀팁 TOP3 조회")
    public Response<List<PostResponse>> getTop3TipPosts(@PathVariable Long certificateId) {
        return Response.success(postFetchService.getTop3TipPosts(certificateId));
    }

    @GetMapping("/my/{postType}/posts")
    @Operation(summary = "내가 쓴 글 조회")
    public Response<Slice<PostResponse>> getMyPosts(@PathVariable PostType postType,
                                                   @AuthenticationPrincipal UserDTO user,
                                                   @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(postFetchService.getMyPosts(postType, user.getId(), pageable));
    }

    @GetMapping("/my/comment-posts")
    @Operation(summary = "내가 댓글 쓴 게시글 조회")
    public Response<Page<PostResponse>> getMyCommentPosts(@AuthenticationPrincipal UserDTO user,
                                                          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(postFetchService.getMyCommentPosts(user.getId(), pageable));
    }
}
