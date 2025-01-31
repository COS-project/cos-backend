package com.cos.cercat.domain.postsearch;

import java.time.LocalDateTime;

public record PostCommentForSearch(
        Long id,
        Long userId,
        Long postId,
        String content,
        Integer likeCount,
        LocalDateTime createdAt
) {
}
