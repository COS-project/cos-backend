package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.app.usecase.PostUpdateUseCase;
import com.cos.cercat.apis.post.dto.request.PostUpdateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.dto.UserDTO;
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
@Tag(name = "게시글 수정 API")
public class PostUpdateApi {

    private final PostUpdateUseCase postUpdateUseCase;

    @PutMapping(value = "/certificates/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 수정")
    public Response<Void> updateCommentaryPost(@PathVariable Long certificateId,
                                               @PathVariable PostType postType,
                                               @RequestPart PostUpdateRequest request,
                                               @RequestPart(required = false) List<MultipartFile> images,
                                               @AuthenticationPrincipal UserDTO user) {

        postUpdateUseCase.updatePost(certificateId, postType, request, images, user.getId());

        return Response.success("게시글 수정 완료");
    }

}
