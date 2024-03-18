package com.cos.cercat.post.dto.request;

public record CommentaryPostSearchCond(
        Integer examYear,
        Integer round,
        Integer questionSequence
) {
}
