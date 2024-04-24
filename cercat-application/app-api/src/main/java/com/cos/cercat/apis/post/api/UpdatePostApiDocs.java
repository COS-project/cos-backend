package com.cos.cercat.apis.post.api;


import com.cos.cercat.apis.post.request.PostUpdateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "게시글 수정 API")
public interface UpdatePostApiDocs {


    @Operation(summary = "게시글 수정")
    Response<TargetPost> updateCommentaryPost(Long certificateId,
                                              PostType postType,
                                              PostUpdateRequest request,
                                              List<MultipartFile> files,
                                              UserDTO currentUser);
}
