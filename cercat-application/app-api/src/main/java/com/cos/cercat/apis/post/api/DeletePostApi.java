package com.cos.cercat.apis.post.api;

import com.cos.cercat.common.domain.Response;
import com.cos.cercat.post.DeletePostService;
import com.cos.cercat.post.TargetComment;
import com.cos.cercat.post.TargetPost;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
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

    private final DeletePostService deletePostService;

    @DeleteMapping("/posts/{postId}")
    public Response<Void> deletePost(@PathVariable Long postId,
                                     @AuthenticationPrincipal User currentUser) {
        deletePostService.deletePost(TargetUser.from(currentUser.getId()), TargetPost.from(postId));
        return Response.success("게시글 삭제 성공");
    }

    @DeleteMapping("/post-comments/{commentId}")
    public Response<Void> deletePostComment(@PathVariable Long commentId,
                                            @AuthenticationPrincipal User currentUser) {
        deletePostService.deletePostComment(TargetUser.from(currentUser.getId()), TargetComment.from(commentId));
        return Response.success("댓글 삭제 성공");
    }

}
