package com.cos.cercat.board.api;

import com.cos.cercat.board.app.PostCreateService;
import com.cos.cercat.board.dto.request.CommentaryPostCreateRequest;
import com.cos.cercat.board.dto.request.NormalPostCreateRequest;
import com.cos.cercat.board.dto.request.TipPostCreateRequest;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostCreateApi {

    private final PostCreateService postCreateService;

    @PostMapping(path = "/{certificateId}/commentary-posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "해설 게시글 생성")
    public Response<Void> createCommentaryPost(@PathVariable Long certificateId,
                                               @RequestPart CommentaryPostCreateRequest request,
                                               @RequestPart List<MultipartFile> images) {
        postCreateService.createCommentaryPost(certificateId, request, images, null);
        return Response.success("해설 게시글 생성 완료");
    }

    @PostMapping(path = "/{certificateId}/tip-posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "꿀팁 게시글 생성")
    public Response<Void> createTipPost(@PathVariable Long certificateId,
                                        @RequestPart TipPostCreateRequest request,
                                        @RequestPart List<MultipartFile> images) {
        postCreateService.createTipPost(certificateId, request, images, null);
        return Response.success("꿀팁 게시글 생성 완료");
    }

    @PostMapping(path = "/{certificateId}/free-posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "자유 게시글 생성")
    public Response<Void> createFreePost(@PathVariable Long certificateId,
                                         @RequestPart NormalPostCreateRequest request,
                                         @RequestPart List<MultipartFile> images) {
        postCreateService.createNormalPost(certificateId, request, images, null);
        return Response.success("자유 게시글 생성 완료");
    }
}
