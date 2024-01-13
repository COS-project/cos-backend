package com.cos.cercat.board.dto.request;

public record CommentaryPostCreateRequest(
        String title,
        String content,
        Integer examYear,
        Integer round,
        Integer questionSequence
) {

}
