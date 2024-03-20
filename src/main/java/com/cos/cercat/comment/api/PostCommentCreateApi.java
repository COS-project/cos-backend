package com.cos.cercat.comment.api;

import com.cos.cercat.comment.app.facade.PostCommentCreateService;
import com.cos.cercat.comment.dto.request.PostCommentCreateRequest;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "댓글 생성 API")
public class PostCommentCreateApi {

    private final PostCommentCreateService postCommentCreateService;

    @PostMapping("/posts/{postId}/post-comments")
    @Operation(summary = "댓글 생성")
    public Response<Void> createPostComment(@PathVariable Long postId,
                                            @RequestBody PostCommentCreateRequest request,
                                            @AuthenticationPrincipal UserDTO user) {
        postCommentCreateService.createPostComment(postId, request, user.getId());
        return Response.success("댓글 생성 성공");
    }
}
