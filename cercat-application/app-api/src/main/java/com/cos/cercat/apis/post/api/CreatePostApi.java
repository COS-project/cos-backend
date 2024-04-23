package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.dto.request.PostCommentCreateRequest;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.cos.cercat.apis.global.util.FileMapper.toFiles;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "게시글/댓글 생성 API")
public class CreatePostApi {

    private final CreatePostService createPostService;

    @PostMapping(path = "/certificates/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 생성")
    public Response<TargetPost> createPost(@PathVariable Long certificateId,
                                           @PathVariable PostType postType,
                                           @RequestPart PostCreateRequest request,
                                           @RequestPart(required = false) List<MultipartFile> files,
                                           @AuthenticationPrincipal UserDTO currentUser) {
        TargetPost targetPost;
        switch (postType) {
            case COMMENTARY ->
                targetPost = createPostService.createCommentaryPost(
                        TargetUser.from(currentUser.getId()),
                        TargetCertificate.from(certificateId),
                        request.toContent(),
                        request.toMockExamSession(),
                        request.questionSequence(),
                        toFiles(files)
                );
            case NORMAL->
                targetPost = createPostService.createNormalPost(
                        TargetUser.from(currentUser.getId()),
                        TargetCertificate.from(certificateId),
                        request.toContent(),
                        toFiles(files)
                );
            case TIP ->
                targetPost = createPostService.createTipPost(
                        TargetUser.from(currentUser.getId()),
                        TargetCertificate.from(certificateId),
                        request.toContent(),
                        request.tags(),
                        toFiles(files)
                );
            default -> throw new CustomException(ErrorCode.UNKNOWN_POST_TYPE);
        }
        return Response.success(targetPost);
    }

    @PostMapping("/posts/{postId}/post-comments")
    @Operation(summary = "댓글 생성")
    public Response<Void> createPostComment(@PathVariable Long postId,
                                            @RequestBody PostCommentCreateRequest request,
                                            @AuthenticationPrincipal UserDTO currentUser) {
        createPostService.createPostComment(
                TargetUser.from(currentUser.getId()),
                TargetPost.from(postId),
                request.toCommentContent()
        );
        return Response.success("댓글 생성 성공");
    }

}
