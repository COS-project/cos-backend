package com.cos.cercat.domain.search.dto;

import com.cos.cercat.domain.post.domain.PostType;

public record SearchCond(
        PostType postType,
        String keyword
) {
}
