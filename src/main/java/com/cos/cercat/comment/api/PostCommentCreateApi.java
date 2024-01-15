package com.cos.cercat.comment.api;

import com.cos.cercat.comment.app.PostCommentCreateService;
import com.cos.cercat.comment.dto.request.PostCommentCreateRequest;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "댓글 생성 API")
public class PostCommentCreateApi {

    private final PostCommentCreateService postCommentCreateService;

    @PostMapping("/post-comments")
    @Operation(summary = "댓글 생성")
    public Response<Void> createPostComment(@RequestBody PostCommentCreateRequest request) {
        postCommentCreateService.createPostComment(request, 1L);
        return Response.success("댓글 생성 성공");
    }
}
