package com.cos.cercat.dto;

import com.cos.cercat.domain.post.PostType;

public record SearchCond(
        PostType postType,
        String keyword
) {
}
