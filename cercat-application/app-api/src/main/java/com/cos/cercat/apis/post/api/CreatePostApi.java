package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.dto.request.PostCreateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.post.CreatePostService;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import com.cos.cercat.gcs.FileUploader;
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
@RequestMapping("/api/v2")
@Tag(name = "게시글 생성 API")
public class CreatePostApi {

    private final CreatePostService createPostService;
    private final FileUploader fileUploader;

    @PostMapping(path = "/certificates/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 생성")
    public Response<TargetPost> createPost(@PathVariable Long certificateId,
                                      @PathVariable PostType postType,
                                      @RequestPart PostCreateRequest request,
                                      @RequestPart(required = false) List<MultipartFile> images,
                                      @AuthenticationPrincipal UserDTO currentUser) {
        List<String> imageUrls = fileUploader.uploadFileInStorage2(images);
        TargetPost targetPost;

        switch (postType) {
            case COMMENTARY ->
                targetPost = createPostService.createCommentaryPost(
                        TargetUser.from(currentUser.getId()),
                        TargetCertificate.from(certificateId),
                        request.toContent(postType, imageUrls),
                        request.toMockExamSession(),
                        request.questionSequence()
                );
            case NORMAL->
                targetPost = createPostService.createNormalPost(
                        TargetUser.from(currentUser.getId()),
                        TargetCertificate.from(certificateId),
                        request.toContent(postType, imageUrls)
                );
            case TIP ->
                targetPost = createPostService.createTipPost(
                        TargetUser.from(currentUser.getId()),
                        TargetCertificate.from(certificateId),
                        request.toContent(postType, imageUrls),
                        request.tags()
                );
            default -> throw new CustomException(ErrorCode.UNKNOWN_POST_TYPE);
        }
        return Response.success(targetPost);
    }
}
