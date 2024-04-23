package com.cos.cercat.apis.like.dto.request;

import com.cos.cercat.domain.like.Like;
import com.cos.cercat.domain.like.LikeTargetType;

public record ReadLikeStatusRequest(
        LikeTargetType likeTargetType,
        Long targetId
) {
    public Like toLike() {
        return new Like(
                targetId,
                likeTargetType
        );
    }
}
