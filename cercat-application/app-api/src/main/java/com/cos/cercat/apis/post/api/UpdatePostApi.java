package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.request.PostUpdateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.post.PostType;
import com.cos.cercat.post.TargetPost;
import com.cos.cercat.post.UpdatePostService;
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
public class UpdatePostApi implements UpdatePostApiDocs {

    private final UpdatePostService updatePostService;

    @PutMapping(value = "/certificates/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<TargetPost> updatePost(@PathVariable Long certificateId,
                                           @PathVariable PostType postType,
                                           @RequestPart PostUpdateRequest request,
                                           @RequestPart(required = false) List<MultipartFile> files,
                                           @AuthenticationPrincipal User currentUser) {
        TargetPost targetPost = updatePostService.updatePost(
                TargetUser.from(currentUser.getId()),
                TargetPost.from(request.postId()),
                request.toUpdatedPost(postType),
                toFiles(files)
        );
        return Response.success(targetPost);
    }

}
