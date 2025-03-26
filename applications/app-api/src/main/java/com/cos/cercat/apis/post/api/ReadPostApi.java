package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.response.PostResponse;
import com.cos.cercat.apis.post.response.PostWithCommentsResponse;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.apis.global.annotation.CursorDefault;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.searchlog.SearchCond;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.*;

import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class ReadPostApi implements ReadPostApiDocs {

    private final ReadPostService readPostService;

    @GetMapping("/certificates/{certificateId}/posts")
    public Response<SliceResult<PostResponse>> searchPosts(
            SearchCond cond,
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long certificateId,
            @CursorDefault Cursor cursor
    ) {
        SliceResult<Post> posts = readPostService.searchPosts(
                UserId.from(currentUser.getId()),
                CertificateId.from(certificateId),
                cond,
                cursor
        );
        return Response.success(posts.map(PostResponse::from));
    }

    @GetMapping("/certificates/{certificateId}/commentary-posts")
    public Response<SliceResult<PostResponse>> searchCommentaryPosts(@PathVariable Long certificateId,
                                                                     CommentarySearchCond cond,
                                                                     @CursorDefault Cursor cursor) {
        SliceResult<Post> posts = readPostService.searchCommentaryPost(CertificateId.from(certificateId), cond, cursor);
        return Response.success(posts.map(PostResponse::from));
    }

    @GetMapping("/posts/{postId}")
    public Response<PostWithCommentsResponse> readPostDetail(@PathVariable Long postId) {
        PostWithComments postWithComments = readPostService.readDetail(PostId.from(postId));
        return Response.success(PostWithCommentsResponse.from(postWithComments));
    }

    @GetMapping("/certificates/{certificateId}/tip-posts/best")
    public Response<List<PostResponse>> readTop3TipPosts(@PathVariable Long certificateId) {
        return Response.success(readPostService.readTop3TipPosts(CertificateId.from(certificateId)).stream()
                .map(PostResponse::from)
                .toList());
    }

    @GetMapping("/{postType}/posts/my-posts")
    public Response<SliceResult<PostResponse>> readMyPosts(@PathVariable PostType postType,
                                                           @AuthenticationPrincipal User currentUser,
                                                           @CursorDefault Cursor cursor) {

        SliceResult<Post> posts = readPostService.readMyPosts(UserId.from(currentUser.getId()), postType, cursor);
        return Response.success(posts.map(PostResponse::from));
    }

    @GetMapping("/comment-posts/my-comment-posts")
    public Response<SliceResult<PostResponse>> readMyCommentPosts(@AuthenticationPrincipal User currentUser,
                                                                  @CursorDefault Cursor cursor) {
        SliceResult<Post> posts = readPostService.readCommentingPosts(UserId.from(currentUser.getId()), cursor);
        return Response.success(posts.map(PostResponse::from));
    }


}
