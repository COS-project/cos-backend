package com.cos.cercat.apis.post.response;

import com.cos.cercat.apis.user.response.UserResponse;
import com.cos.cercat.domain.like.LikeStatus;
import com.cos.cercat.domain.post.DateTime;
import com.cos.cercat.domain.post.PostComment;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public static PostCommentResponse of(PostComment comment, Map<Long, LikeStatus> likeStatusMap) {
        LikeStatus parentLikeStatus = likeStatusMap.get(comment.getId());

        List<PostCommentResponse> childResponses = comment.getChildComments().stream()
                .map(child -> PostCommentResponse.of(child, likeStatusMap))
                .collect(Collectors.toList());

        return new PostCommentResponse(
                comment.getId(),
                UserResponse.from(comment.getCommenter()),
                comment.hasParent() ? comment.getContent().parentId() : null,
                comment.getContent().content(),
                comment.getDateTime(),
                childResponses,
                parentLikeStatus.count().value(),
                parentLikeStatus.isLiked()
        );
    }

}
