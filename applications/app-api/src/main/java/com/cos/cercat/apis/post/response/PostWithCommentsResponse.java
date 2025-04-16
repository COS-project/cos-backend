package com.cos.cercat.apis.post.response;

import com.cos.cercat.domain.like.LikeStatus;
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
            LikeStatus postLikeStatus,
            Map<Long, LikeStatus> postCommentLikeStatusMap
    ) {
        return new PostWithCommentsResponse(
                PostResponse.from(postWithComments.post(), postLikeStatus),
                postWithComments.postComments().stream()
                        .map(
                                postComment -> PostCommentResponse.of(
                                        postComment,
                                        postCommentLikeStatusMap))
                        .collect(Collectors.toList())
        );
    }
}
