package com.cos.cercat.apis.comment.dto.response;

import com.cos.cercat.apis.user.dto.response.UserResponse;
import com.cos.cercat.domain.comment.PostCommentEntity;
import com.cos.cercat.domain.post.DateTime;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.post.PostCommentWithChild;
import com.cos.cercat.domain.post.PostWithComments;
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
        Long parentCommentId,
        Integer likeCount,
        boolean isLiked,
        String content,
        DateTime dateTime,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<PostCommentResponse> childPostComments
) {
    public static PostCommentResponse of(PostComment postComment, boolean isLiked) {
        return new PostCommentResponse(
                postComment.id(),
                UserResponse.from(postComment.user()),
                postComment.parentId(),
                postComment.likeCount(),
                isLiked,
                postComment.content(),
                postComment.dateTime(),
                postComment.childComments().stream()
                        .map(childComment -> PostCommentResponse.of(childComment, isLiked))
                        .toList()
        );
    }
}
