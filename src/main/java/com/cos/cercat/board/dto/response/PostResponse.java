package com.cos.cercat.board.dto.response;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.user.dto.response.UserResponse;

import java.time.LocalDateTime;

public record PostResponse(
        Long postId,
        String title,
        String content,
        UserResponse user,
        Integer likeCount,
        Integer commentCount,
        LocalDateTime createdAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                UserResponse.from(post.getUser()),
                post.getLikeCount(),
                post.getPostComments().countComments(),
                post.getCreatedAt()
        );
    }
}
