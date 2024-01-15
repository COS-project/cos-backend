package com.cos.cercat.board.api;

import com.cos.cercat.board.app.PostUpdateService;
import com.cos.cercat.board.dto.request.CommentaryPostUpdateRequest;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시글 수정 API")
public class PostUpdateApi {

    private final PostUpdateService postUpdateService;

    @PutMapping("/{certificateId}/commentary-posts")
    @Operation(summary = "해설 게시글 수정")
    public Response<Void> updateCommentaryPost(@PathVariable Long certificateId,
                                               @RequestPart CommentaryPostUpdateRequest request,
                                               @RequestPart List<MultipartFile> images) {

        postUpdateService.updateCommentaryPost(certificateId, request, images);

        return Response.success("해설 게시글 수정 완료");
    }

}
