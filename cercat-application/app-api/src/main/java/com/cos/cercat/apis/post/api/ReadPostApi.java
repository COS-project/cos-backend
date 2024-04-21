package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.comment.dto.response.PostCommentResponse;
import com.cos.cercat.apis.global.util.CursorConvertor;
import com.cos.cercat.apis.post.dto.response.PostResponse;
import com.cos.cercat.apis.post.dto.response.PostWithCommentsResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.like.LikeService;
import com.cos.cercat.domain.like.LikeTargetType;
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
    private final LikeService likeService;

    @GetMapping("/certificates/{certificateId}/posts")
    @Operation(summary = "해설 게시글 검색")
    public Response<SliceResult<PostResponse>> searchCommentaryPosts(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                     @PathVariable Long certificateId,
                                                                     CommentaryPostSearchCond cond,
                                                                     @AuthenticationPrincipal UserDTO currentUser) {
        SliceResult<Post> posts = readPostService.searchCommentaryPost(TargetCertificate.from(certificateId), cond, CursorConvertor.convertCursorToPage(pageable));

        List<PostResponse> responses = posts.content().stream()
                .map(post -> getPostResponse(post.getId(), currentUser, post))
                .toList();
        return Response.success(SliceResult.of(responses, posts.hasNext()));
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public Response<PostWithCommentsResponse> getPostDetail(@PathVariable Long postId,
                                                            @AuthenticationPrincipal UserDTO currentUser) {
        PostWithComments postWithComments = readPostService.readDetail(TargetPost.from(postId));
        PostResponse postResponse = getPostResponse(postId, currentUser, postWithComments.post());
        List<PostCommentResponse> postCommentResponses = toPostCommentResponse(currentUser, postWithComments.postComments());

        return Response.success(PostWithCommentsResponse.of(postResponse, postCommentResponses));
    }

    private PostResponse getPostResponse(Long postId, UserDTO currentUser, Post post) {
        return PostResponse.of(post, likeService.isLiked(LikeTargetType.POST, TargetUser.from(currentUser.getId()), postId));
    }

    private List<PostCommentResponse> toPostCommentResponse(UserDTO currentUser, List<PostComment> postComments) {
        return postComments.stream()
                .map(postComment -> PostCommentResponse.of(
                        postComment,
                        likeService.isLiked(LikeTargetType.COMMENT, TargetUser.from(currentUser.getId()), postComment.id()))
                )
                .toList();
    }

}
