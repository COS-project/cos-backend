package com.cos.cercat.apis.post.api;


import com.cos.cercat.apis.post.request.PostUpdateRequest;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.PostId;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "게시글 수정 API")
public interface UpdatePostApiDocs {


    @Operation(summary = "게시글 수정")
    Response<PostId> updatePost(Long certificateId,
                                    PostType postType,
                                    PostUpdateRequest request,
                                    List<MultipartFile> files,
                                    User currentUser);
}
