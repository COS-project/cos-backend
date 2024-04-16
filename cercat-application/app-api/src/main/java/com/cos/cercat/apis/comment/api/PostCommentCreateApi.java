package com.cos.cercat.apis.comment.api;

import com.cos.cercat.apis.comment.app.usecase.PostCommentCreateUseCase;
import com.cos.cercat.apis.comment.dto.request.PostCommentCreateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
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

    private final PostCommentCreateUseCase postCommentCreateUseCase;

    @PostMapping("/posts/{postId}/post-comments")
    @Operation(summary = "댓글 생성")
    public Response<Void> createPostComment(@PathVariable Long postId,
                                            @RequestBody PostCommentCreateRequest request,
                                            @AuthenticationPrincipal UserDTO currentUser) {
        postCommentCreateUseCase.createPostComment(postId, request, currentUser.getId());
        return Response.success("댓글 생성 성공");
    }
}
