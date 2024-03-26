package com.cos.cercat.apis.comment.dto.response;

import com.cos.cercat.apis.user.dto.response.UserResponse;
import com.cos.cercat.domain.comment.PostComment;
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
        boolean isLiked,
        String content,
        List<PostCommentResponse> childPostComments
) {

    public static PostCommentResponse of(Long id, UserResponse user, LocalDateTime createdAt, Long parentCommentId, Integer likeCount, boolean isLiked, String content) {
        return new PostCommentResponse(id, user, createdAt, parentCommentId, likeCount, isLiked, content, new ArrayList<>());
    }

    public static PostCommentResponse of(PostComment entity, boolean isLiked) {
        return PostCommentResponse.of(
                entity.getId(),
                UserResponse.fromEntity(entity.getUser()),
                entity.getCreatedAt(),
                entity.getParentCommentId(),
                entity.getLikeCount(),
                isLiked,
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
