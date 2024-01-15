package com.cos.cercat.board.dto.response;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.user.dto.response.UserResponse;

public record CommentaryPostResponse(
        PostResponse postInfo,
        Integer questionSequence,
        MockExamResponse mockExam
) {
        public static CommentaryPostResponse from(CommentaryPost commentaryPost) {
                return new CommentaryPostResponse(
                        PostResponse.from(commentaryPost),
                        commentaryPost.getQuestion().getQuestionSeq(),
                        MockExamResponse.from(commentaryPost.getQuestion().getMockExam())
                );
        }
}
