package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.response.PostResponse;
import com.cos.cercat.apis.post.response.PostWithCommentsResponse;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.apis.global.annotation.CursorDefault;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.like.LikeService;
import com.cos.cercat.domain.like.LikeStatus;
import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeTargetType;
import com.cos.cercat.domain.searchlog.SearchCond;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class ReadPostApi implements ReadPostApiDocs {

    private final ReadPostService readPostService;
    private final LikeService likeService;

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

        Map<Long, LikeStatus> likeStatusMap = getLikeStatusMap(posts.content(), currentUser);

        return Response.success(posts.map(post -> createPostResponse(post, likeStatusMap)));
    }

    @GetMapping("/certificates/{certificateId}/commentary-posts")
    public Response<SliceResult<PostResponse>> searchCommentaryPosts(
            @PathVariable Long certificateId,
            @AuthenticationPrincipal User currentUser,
            CommentarySearchCond cond,
            @CursorDefault Cursor cursor
    ) {
        SliceResult<Post> posts = readPostService.searchCommentaryPost(
                CertificateId.from(certificateId),
                cond,
                cursor
        );

        Map<Long, LikeStatus> likeStatusMap = getLikeStatusMap(posts.content(), currentUser);

        return Response.success(posts.map(post -> createPostResponse(post, likeStatusMap)));
    }

    @GetMapping("/posts/{postId}")
    public Response<PostWithCommentsResponse> readPostDetail(
            @PathVariable Long postId,
            @AuthenticationPrincipal User currentUser
    ) {
        PostWithComments postWithComments = readPostService.readDetail(PostId.from(postId));
        LikeStatus likeStatus = likeService.getLikeStatus(
                UserId.from(currentUser.getId()),
                LikeTarget.post(postWithComments.post().getId())
        );

        Map<Long, LikeStatus> commentLikeStatusMap = getCommentLikeStatusMap(
                postWithComments.postComments(), currentUser);

        return Response.success(PostWithCommentsResponse.of(postWithComments, likeStatus, commentLikeStatusMap));
    }

    @GetMapping("/certificates/{certificateId}/tip-posts/best")
    public Response<List<PostResponse>> readTop3TipPosts(
            @PathVariable Long certificateId,
            @AuthenticationPrincipal User currentUser
    ) {
        List<Post> posts = readPostService.readTop3TipPosts(CertificateId.from(certificateId));

        Map<Long, LikeStatus> likeStatusMap = getLikeStatusMap(posts, currentUser);

        return Response.success(posts.stream()
                .map(post -> createPostResponse(post, likeStatusMap))
                .toList());
    }

    @GetMapping("/{postType}/posts/my-posts")
    public Response<SliceResult<PostResponse>> readMyPosts(
            @PathVariable PostType postType,
            @AuthenticationPrincipal User currentUser,
            @CursorDefault Cursor cursor
    ) {
        SliceResult<Post> posts = readPostService.readMyPosts(
                UserId.from(currentUser.getId()),
                postType,
                cursor
        );

        Map<Long, LikeStatus> likeStatusMap = getLikeStatusMap(posts.content(), currentUser);

        return Response.success(posts.map(post -> createPostResponse(post, likeStatusMap)));
    }

    @GetMapping("/comment-posts/my-comment-posts")
    public Response<SliceResult<PostResponse>> readMyCommentPosts(
            @AuthenticationPrincipal User currentUser,
            @CursorDefault Cursor cursor
    ) {
        SliceResult<Post> posts = readPostService.readCommentingPosts(
                UserId.from(currentUser.getId()),
                cursor
        );

        Map<Long, LikeStatus> likeStatusMap = getLikeStatusMap(posts.content(), currentUser);

        return Response.success(posts.map(post -> createPostResponse(post, likeStatusMap)));
    }

    /**
     * 게시물 목록에 대한 좋아요 상태 맵 조회
     */
    private Map<Long, LikeStatus> getLikeStatusMap(List<Post> posts, User currentUser) {
        return likeService.getLikeStatusMap(
                UserId.from(currentUser.getId()),
                posts.stream()
                        .map(post -> LikeTarget.post(post.getId()))
                        .toList()
        );
    }

    private Map<Long, LikeStatus> getCommentLikeStatusMap(List<PostComment> comments, User currentUser) {
        return likeService.getLikeStatusMap(
                UserId.from(currentUser.getId()),
                comments.stream()
                        .map(comment -> LikeTarget.comment(comment.getId()))
                        .toList()
        );
    }

    private PostResponse createPostResponse(Post post, Map<Long, LikeStatus> likeStatusMap) {
        return PostResponse.from(
                post,
                likeStatusMap.get(post.getId())
        );
    }
}
