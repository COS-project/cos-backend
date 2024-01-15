package com.cos.cercat.board.dto.response;

import com.cos.cercat.board.domain.TipPost;

public record TipPostResponse(
        PostResponse postInfo
) {
    public static TipPostResponse from(TipPost entity) {
        return new TipPostResponse(
                PostResponse.from(entity)
        );
    }
}
