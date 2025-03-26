package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.request.PostUpdateRequest;
import com.cos.cercat.domain.post.PostService;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.PostId;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
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

    private final PostService postService;

    @PutMapping(value = "/certificates/{certificateId}/{postType}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<PostId> updatePost(@PathVariable Long certificateId,
                                           @PathVariable PostType postType,
                                           @RequestPart PostUpdateRequest request,
                                           @RequestPart(required = false) List<MultipartFile> files,
                                           @AuthenticationPrincipal User currentUser) {
        PostId postId = postService.updatePost(
                UserId.from(currentUser.getId()),
                PostId.from(request.postId()),
                request.toUpdatedPost(postType),
                toFiles(files)
        );
        return Response.success(postId);
    }

}
