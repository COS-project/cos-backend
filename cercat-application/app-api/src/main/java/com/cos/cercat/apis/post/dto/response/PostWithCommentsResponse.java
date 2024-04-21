package com.cos.cercat.apis.post.dto.response;

import com.cos.cercat.apis.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.apis.user.dto.response.UserResponse;
import com.cos.cercat.apis.comment.dto.response.PostCommentResponse;
import com.cos.cercat.apis.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.apis.post.dto.RecommendTagDTO;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.post.NormalPostEntity;
import com.cos.cercat.domain.post.PostEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostWithCommentsResponse(
        PostResponse postResponse,
        List<PostCommentResponse> postComments
) {

    public static PostWithCommentsResponse of(PostResponse postResponse, List<PostCommentResponse> commentResponses) {
        return new PostWithCommentsResponse(
                postResponse,
                commentResponses
        );
    }
}
