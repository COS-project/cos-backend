package com.cos.cercat.comment.api;

import com.cos.cercat.comment.app.facade.PostCommentDeleteService;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
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
@RequestMapping("/api/v1")
@Tag(name = "댓글 삭제 API")
public class PostCommentDeleteApi {

    private final PostCommentDeleteService postCommentDeleteService;

    @DeleteMapping("/post-comments/{commentId}")
    @Operation(summary = "댓글 삭제")
    public Response<Void> deletePostComment(@PathVariable Long commentId,
                                            @AuthenticationPrincipal UserDTO user) {
        postCommentDeleteService.deletePostComment(commentId, user.getId());
        return Response.success("댓글 삭제 성공");
    }
}
