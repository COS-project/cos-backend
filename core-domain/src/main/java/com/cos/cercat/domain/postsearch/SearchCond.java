package com.cos.cercat.domain.postsearch;


import com.cos.cercat.domain.post.PostType;

public record SearchCond(
        PostType postType,
        String keyword
) {
    public SearchLog toSearchLog() {
        return new SearchLog(keyword);
    }
}
