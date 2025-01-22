package com.cos.cercat.apis.like.request;


import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeTargetType;

public record LikeRequest(
        LikeTargetType likeTargetType,
        Long targetId
) {
    public LikeTarget toLike() {
        return new LikeTarget(
                targetId,
                likeTargetType
        );
    }
}
