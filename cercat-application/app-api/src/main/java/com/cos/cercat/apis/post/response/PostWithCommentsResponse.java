package com.cos.cercat.apis.post.response;

import com.cos.cercat.domain.post.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostWithCommentsResponse(
        PostResponse postResponse,
        List<PostCommentResponse> postComments
) {
    public static PostWithCommentsResponse from(PostWithComments postWithComments) {
        return new PostWithCommentsResponse(
                PostResponse.from(postWithComments.post()),
                postWithComments.postComments().stream()
                        .map(PostCommentResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
