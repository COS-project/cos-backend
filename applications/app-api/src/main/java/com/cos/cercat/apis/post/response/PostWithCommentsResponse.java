package com.cos.cercat.apis.post.response;

import com.cos.cercat.domain.like.LikeStatus;
import com.cos.cercat.domain.like.LikeTargetType;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.post.PostWithComments;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostWithCommentsResponse(
        PostResponse postResponse,
        List<PostCommentResponse> postComments
) {
    public static PostWithCommentsResponse of(
            PostWithComments postWithComments,
            LikeStatus likeStatus,
            Map<Long, LikeStatus> postCommentLikeStatusMap
    ) {
        return new PostWithCommentsResponse(
                PostResponse.from(postWithComments.post(), likeStatus),
                postWithComments.postComments().stream()
                        .map(
                                postComment -> PostCommentResponse.of(
                                        postComment,
                                        getCommentLikeStatus(postCommentLikeStatusMap, postComment)))
                        .collect(Collectors.toList())
        );
    }

    private static LikeStatus getCommentLikeStatus(Map<Long, LikeStatus> postCommentLikeStatusMap,
            PostComment postComment) {
        return postCommentLikeStatusMap.get(postComment.getId());
    }
}
