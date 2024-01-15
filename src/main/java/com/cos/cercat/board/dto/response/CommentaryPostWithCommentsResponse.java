package com.cos.cercat.board.dto.response;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.comment.dto.response.PostCommentResponse;
import com.cos.cercat.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.user.dto.response.UserResponse;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record CommentaryPostWithCommentsResponse(
        PostWithCommentsResponse postWithCommentInfo,
        QuestionResponse question,
        MockExamResponse mockExam
) {
    public static CommentaryPostWithCommentsResponse from(CommentaryPost entity) {
        return new CommentaryPostWithCommentsResponse(
                PostWithCommentsResponse.from(entity),
                QuestionResponse.from(entity.getQuestion()),
                MockExamResponse.from(entity.getQuestion().getMockExam())
        );
    }
}
