package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.global.util.CursorConvertor;
import com.cos.cercat.apis.post.dto.response.PostResponse;
import com.cos.cercat.apis.post.dto.response.PostWithCommentsResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "게시글 조회 API")
public class ReadPostApi {

    private final ReadPostService readPostService;

    @GetMapping("/certificates/{certificateId}/posts")
    @Operation(summary = "해설 게시글 검색")
    public Response<SliceResult<PostResponse>> searchCommentaryPosts(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                     @PathVariable Long certificateId,
                                                                     CommentaryPostSearchCond cond) {
        SliceResult<Post> posts = readPostService.searchCommentaryPost(TargetCertificate.from(certificateId), cond, CursorConvertor.toCursor(pageable));

        return Response.success(posts.map(PostResponse::from));
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public Response<PostWithCommentsResponse> getPostDetail(@PathVariable Long postId) {
        PostWithComments postWithComments = readPostService.readDetail(TargetPost.from(postId));
        return Response.success(PostWithCommentsResponse.from(postWithComments));
    }

    @GetMapping("/certificates/{certificateId}/tip-posts/best")
    @Operation(summary = "베스트 꿀팁 TOP3 조회")
    public Response<List<PostResponse>> getTop3TipPosts(@PathVariable Long certificateId) {
        return Response.success(readPostService.readTop3TipPosts(TargetCertificate.from(certificateId)).stream()
                .map(PostResponse::from)
                .toList());
    }

    @GetMapping("/{postType}/posts/my-posts")
    @Operation(summary = "내가 쓴 글 조회")
    public Response<SliceResult<PostResponse>> getMyPosts(@PathVariable PostType postType,
                                                    @AuthenticationPrincipal UserDTO currentUser,
                                                    @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        SliceResult<Post> posts = readPostService.readMyPosts(TargetUser.from(currentUser.getId()), postType, CursorConvertor.toCursor(pageable));
        return Response.success(posts.map(PostResponse::from));
    }

    @GetMapping("/comment-posts/my-comment-posts")
    @Operation(summary = "내가 댓글 쓴 게시글 조회")
    public Response<SliceResult<PostResponse>> getMyCommentPosts(@AuthenticationPrincipal UserDTO currentUser,
                                                           @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        SliceResult<Post> posts = readPostService.readCommentingPosts(TargetUser.from(currentUser.getId()), CursorConvertor.toCursor(pageable));
        return Response.success(posts.map(PostResponse::from));
    }


}
