package com.cos.cercat.apis.like.request;


import com.cos.cercat.domain.like.Like;
import com.cos.cercat.domain.like.LikeTargetType;

public record LikeRequest(
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
