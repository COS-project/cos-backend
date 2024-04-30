package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.request.PostCommentCreateRequest;
import com.cos.cercat.apis.post.request.PostCreateRequest;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.post.CreatePostService;
import com.cos.cercat.post.PostType;
import com.cos.cercat.post.TargetPost;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
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
public class CreatePostApi implements CreatePostApiDocs {

    private final CreatePostService createPostService;

    @PostMapping(path = "/certificates/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<TargetPost> createPost(@PathVariable Long certificateId,
                                           @PathVariable PostType postType,
                                           @RequestPart PostCreateRequest request,
                                           @RequestPart(required = false) List<MultipartFile> files,
                                           @AuthenticationPrincipal User currentUser) {
        TargetPost targetPost = createPostService.createPost(
                TargetUser.from(currentUser.getId()),
                TargetCertificate.from(certificateId),
                request.toNewPost(postType),
                toFiles(files)
        );
        return Response.success(targetPost);
    }

    @PostMapping("/posts/{postId}/post-comments")
    public Response<Void> createPostComment(@PathVariable Long postId,
                                            @RequestBody PostCommentCreateRequest request,
                                            @AuthenticationPrincipal User currentUser) {
        createPostService.createPostComment(
                TargetUser.from(currentUser.getId()),
                TargetPost.from(postId),
                request.toCommentContent()
        );
        return Response.success("댓글 생성 성공");
    }

}
