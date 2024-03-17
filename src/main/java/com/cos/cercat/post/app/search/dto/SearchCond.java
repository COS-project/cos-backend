package com.cos.cercat.post.app.search.dto;

import com.cos.cercat.post.domain.PostType;

public record SearchCond(
        PostType postType,
        String keyword
) {
}
