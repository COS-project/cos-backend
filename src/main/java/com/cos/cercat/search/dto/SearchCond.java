package com.cos.cercat.search.dto;

import com.cos.cercat.post.domain.PostType;

public record SearchCond(
        PostType postType,
        String keyword
) {
}
