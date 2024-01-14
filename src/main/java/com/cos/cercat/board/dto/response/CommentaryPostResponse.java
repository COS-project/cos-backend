package com.cos.cercat.board.dto.response;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.user.dto.response.UserResponse;

public record CommentaryPostResponse(
        Long postId,
        String title,
        String content,
        Integer questionSequence,
        UserResponse user,
        MockExamResponse mockExam,
        Integer likeCount,
        Integer commentCount
) {
        public static CommentaryPostResponse from(CommentaryPost commentaryPost, int likeCount) {
                return new CommentaryPostResponse(
                        commentaryPost.getId(),
                        commentaryPost.getTitle(),
                        commentaryPost.getContent(),
                        commentaryPost.getQuestion().getQuestionSeq(),
                        UserResponse.from(commentaryPost.getUser()),
                        MockExamResponse.from(commentaryPost.getQuestion().getMockExam()),
                        likeCount,
                        0
                );
        }
}
