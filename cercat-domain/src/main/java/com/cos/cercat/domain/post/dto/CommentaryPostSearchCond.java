package com.cos.cercat.domain.post.dto;

public record CommentaryPostSearchCond(
        Integer examYear,
        Integer round,
        Integer questionSequence
) {
}
