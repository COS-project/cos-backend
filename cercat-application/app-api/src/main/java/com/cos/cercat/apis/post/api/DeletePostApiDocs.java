package com.cos.cercat.apis.post.api;

import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "게시글 삭제 API")
public interface DeletePostApiDocs {

    @Operation(summary = "게시글 삭제")
    Response<Void> deletePost(Long postId,
                              User currentUser);

    @Operation(summary = "댓글 삭제")
    Response<Void> deletePostComment(Long commentId,
                                     User currentUser);

}
