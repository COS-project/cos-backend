package com.cos.cercat.search;


import com.cos.cercat.post.PostType;

public record SearchCond(
        PostType postType,
        String keyword
) {
}
