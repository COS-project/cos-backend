package com.cos.cercat.post.api;

import com.cos.cercat.post.app.PostService;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시글 삭제 API")
public class PostDeleteApi {

    private final PostService postService;

    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "게시글 삭제")
    public Response<Void> deletePost(@PathVariable Long postId) {

        postService.deletePost(postId);
        return Response.success("게시글 삭제 성공");
    }
}
