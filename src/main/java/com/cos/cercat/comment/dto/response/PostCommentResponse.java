package com.cos.cercat.comment.dto.response;

import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.user.dto.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public record PostCommentResponse(
        Long postCommentId,
        UserResponse user,
        LocalDateTime createdAt,
        Long parentCommentId,
        Integer likeCount,
        String content,
        List<PostCommentResponse> childPostComments
) {

    public static PostCommentResponse of(Long id, UserResponse user, LocalDateTime createdAt, Long parentCommentId, Integer likeCount, String content) {
        return new PostCommentResponse(id, user, createdAt, parentCommentId, likeCount, content, new ArrayList<>());
    }

    public static PostCommentResponse from(PostComment entity) {
        return PostCommentResponse.of(
                entity.getId(),
                UserResponse.from(entity.getUser()),
                entity.getCreatedAt(),
                entity.getParentCommentId(),
                entity.getLikeCount(),
                entity.getContent()
        );
    }

    public void addChildComment(PostCommentResponse childComment) {
        childPostComments.add(childComment);
        childPostComments.sort(Comparator
                .comparing(PostCommentResponse::createdAt)
                .thenComparingLong(PostCommentResponse::postCommentId));
    }

    public boolean hasParentComment() {
        return parentCommentId != null;
    }
}
