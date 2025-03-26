package com.cos.cercat.apis.post.api;

import com.cos.cercat.domain.post.PostCommentService;
import com.cos.cercat.domain.post.PostService;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.post.CommentId;
import com.cos.cercat.domain.post.PostId;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class DeletePostApi implements DeletePostApiDocs {

    private final PostService postService;
    private final PostCommentService postCommentService;

    @DeleteMapping("/posts/{postId}")
    public Response<Void> deletePost(@PathVariable Long postId,
                                     @AuthenticationPrincipal User currentUser) {
        postService.deletePost(UserId.from(currentUser.getId()), PostId.from(postId));
        return Response.success("게시글 삭제 성공");
    }

    @DeleteMapping("/post-comments/{commentId}")
    public Response<Void> deletePostComment(@PathVariable Long commentId,
                                            @AuthenticationPrincipal User currentUser) {
        postCommentService.deletePostComment(UserId.from(currentUser.getId()), CommentId.from(commentId));
        return Response.success("댓글 삭제 성공");
    }

}
