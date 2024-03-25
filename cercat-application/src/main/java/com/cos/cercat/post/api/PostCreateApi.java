package com.cos.cercat.post.api;

import com.cos.cercat.post.app.usecase.PostCreateUseCase;
import com.cos.cercat.post.dto.request.PostCreateRequest;
import com.cos.cercat.post.domain.PostType;
import com.cos.cercat.dto.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시글 생성 API")
public class PostCreateApi {

    private final PostCreateUseCase postCreateUseCase;

    @PostMapping(path = "/certificates/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 생성")
    public Response<Void> createPost(@PathVariable Long certificateId,
                                     @PathVariable PostType postType,
                                     @RequestPart PostCreateRequest request,
                                     @RequestPart(required = false) List<MultipartFile> images,
                                     @AuthenticationPrincipal UserDTO user) {
        postCreateUseCase.createPost(certificateId, postType, request, images, user.getId());
        return Response.success("게시글 생성 완료");
    }
}
