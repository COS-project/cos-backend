package com.cos.cercat.board.api;

import com.cos.cercat.board.app.PostCreateService;
import com.cos.cercat.board.dto.request.PostCreateRequest;
import com.cos.cercat.board.dto.request.PostType;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시글 생성 API")
public class PostCreateApi {

    private final PostCreateService postCreateService;

    @PostMapping(path = "/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 생성")
    public Response<Void> createPost(@PathVariable Long certificateId,
                                     @PathVariable PostType postType,
                                     @RequestPart PostCreateRequest request,
                                     @RequestPart(required = false) List<MultipartFile> images) {
        postCreateService.createPost(certificateId, postType, request, images, 1L);
        return Response.success("게시글 생성 완료");
    }
}
