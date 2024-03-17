package com.cos.cercat.post.dto.request;

public record PostSearchCond(
        Integer examYear,
        Integer round,
        Integer questionSequence
) {
}
