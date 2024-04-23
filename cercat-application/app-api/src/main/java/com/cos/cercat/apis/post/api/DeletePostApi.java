package com.cos.cercat.apis.post.api;

import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.post.DeletePostService;
import com.cos.cercat.domain.post.TargetComment;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "게시글 삭제 API")
public class DeletePostApi {

    private final DeletePostService deletePostService;

    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "게시글 삭제")
    public Response<Void> deletePost(@PathVariable Long postId,
                                     @AuthenticationPrincipal UserDTO currentUser) {
        deletePostService.deletePost(TargetUser.from(currentUser.getId()), TargetPost.from(postId));
        return Response.success("게시글 삭제 성공");
    }

    @DeleteMapping("/post-comments/{commentId}")
    @Operation(summary = "댓글 삭제")
    public Response<Void> deletePostComment(@PathVariable Long commentId,
                                            @AuthenticationPrincipal UserDTO currentUser) {
        deletePostService.deletePostComment(TargetUser.from(currentUser.getId()), TargetComment.from(commentId));
        return Response.success("댓글 삭제 성공");
    }

}
