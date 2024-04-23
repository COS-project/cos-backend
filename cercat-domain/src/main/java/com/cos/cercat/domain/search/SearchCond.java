package com.cos.cercat.domain.search;


import com.cos.cercat.domain.post.PostType;

public record SearchCond(
        PostType postType,
        String keyword
) {
}
