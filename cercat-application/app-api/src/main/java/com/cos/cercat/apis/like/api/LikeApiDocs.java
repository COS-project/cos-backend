package com.cos.cercat.apis.like.api;

import com.cos.cercat.apis.like.request.ReadLikeStatusRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "좋아요 생성/취소 API")
public interface LikeApiDocs {

    @Operation(summary = "좋아요 생성 및 취소")
    Response<Void> flipPostLike(ReadLikeStatusRequest request, UserDTO currentUser);

    @Operation(summary = "좋아요의 여부 확인")
    Response<Boolean> isLiked(ReadLikeStatusRequest request, UserDTO currentUser);
}
