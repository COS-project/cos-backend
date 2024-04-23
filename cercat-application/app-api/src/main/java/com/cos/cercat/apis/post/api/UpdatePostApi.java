package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.dto.request.PostUpdateRequest;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.post.UpdatePostService;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import com.cos.cercat.gcs.GcsFileUploader;
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
@Tag(name = "게시글 수정 API")
public class UpdatePostApi {

    private final UpdatePostService updatePostService;

    @PutMapping(value = "/certificates/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 수정")
    public Response<TargetPost> updateCommentaryPost(@PathVariable Long certificateId,
                                               @PathVariable PostType postType,
                                               @RequestPart PostUpdateRequest request,
                                               @RequestPart(required = false) List<MultipartFile> files,
                                               @AuthenticationPrincipal UserDTO currentUser) {
        TargetPost targetPost;
        switch (postType) {
            case COMMENTARY -> targetPost = updatePostService.updateCommentaryPost(
                    TargetUser.from(currentUser.getId()),
                    TargetPost.from(request.postId()),
                    TargetCertificate.from(certificateId),
                    request.toPostContent(),
                    request.toMockExamSession(),
                    request.questionSequence(),
                    request.removeImageIds(),
                    toFiles(files)
            );
            case TIP -> targetPost = updatePostService.updateTipPost(
                    TargetUser.from(currentUser.getId()),
                    TargetPost.from(request.postId()),
                    request.toPostContent(),
                    request.newTags(),
                    request.removeImageIds(),
                    toFiles(files)
            );
            case NORMAL -> targetPost = updatePostService.updateNormalPost(
                    TargetUser.from(currentUser.getId()),
                    TargetPost.from(request.postId()),
                    request.toPostContent(),
                    request.removeImageIds(),
                    toFiles(files)
            );
            default -> throw new CustomException(ErrorCode.UNKNOWN_POST_TYPE);
        }
        return Response.success(targetPost);
    }

}
