package com.cos.cercat.board.dto.response;

public record NormalPostResponse(
        String title,
        String content,
        Integer likeCount,
        Integer commentCount
) {

}
