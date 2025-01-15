package com.cos.cercat.apis.like.api;

import com.cos.cercat.apis.like.request.LikeRequest;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.web.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "좋아요 API")
public interface LikeApiDocs {

    @Operation(summary = "좋아요 생성 및 취소")
    Response<Void> flipPostLike(LikeRequest request, User currentUser);

    @Operation(summary = "좋아요의 여부 확인")
    Response<Boolean> isLiked(LikeRequest request, User currentUser);
}
