package com.cos.cercat.board.dto.response;

import com.cos.cercat.board.domain.NormalPost;

public record NormalPostResponse(
        PostResponse postInfo
) {
    public static NormalPostResponse from(NormalPost entity) {
        return new NormalPostResponse(
                PostResponse.from(entity)
        );
    }
}
