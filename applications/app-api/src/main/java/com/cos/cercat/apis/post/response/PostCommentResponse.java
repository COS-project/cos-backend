package com.cos.cercat.apis.post.response;

import com.cos.cercat.apis.user.response.UserResponse;
import com.cos.cercat.domain.like.LikeStatus;
import com.cos.cercat.domain.post.DateTime;
import com.cos.cercat.domain.post.PostComment;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public record PostCommentResponse(
        Long postCommentId,
        UserResponse user,
        Long parentCommentId,
        String content,
        DateTime dateTime,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<PostCommentResponse> childPostComments,
        long likeCount,
        boolean likeStatus
) {
    public static PostCommentResponse of(PostComment postComment, LikeStatus likeStatus) {
        return new PostCommentResponse(
                postComment.getId(),
                UserResponse.from(postComment.getOwner()),
                postComment.getContent().parentId(),
                postComment.getContent().content(),
                postComment.getDateTime(),
                postComment.getChildComments().stream()
                        .map(childPostComment -> PostCommentResponse.of(childPostComment, likeStatus))
                        .toList(),
                likeStatus.count().value(),
                likeStatus.isLiked()
        );
    }
}
