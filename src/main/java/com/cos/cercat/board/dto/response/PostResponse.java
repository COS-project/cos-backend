package com.cos.cercat.board.dto.response;

import com.cos.cercat.board.domain.*;
import com.cos.cercat.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.user.dto.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public record PostResponse(
        Long postId,
        PostType postType,
        String title,
        String content,
        UserResponse user,
        String thumbnailImage,
        Integer likeCount,
        Integer commentCount,
        Integer questionSequence,
        MockExamResponse mockExam,
        LocalDateTime createdAt
) {
    public static PostResponse from(CommentaryPost commentaryPost) {
        return new PostResponse(
                commentaryPost.getId(),
                commentaryPost.getPostType(),
                commentaryPost.getTitle(),
                commentaryPost.getContent(),
                UserResponse.from(commentaryPost.getUser()),
                commentaryPost.getPostImages().getThumbnailImageUrl(),
                commentaryPost.getLikeCount(),
                commentaryPost.getPostComments().countComments(),
                commentaryPost.getQuestion().getQuestionSeq(),
                MockExamResponse.from(commentaryPost.getQuestion().getMockExam()),
                commentaryPost.getCreatedAt()
        );
    }

    public static PostResponse from(TipPost tipPost) {
        return new PostResponse(
                tipPost.getId(),
                tipPost.getPostType(),
                tipPost.getTitle(),
                tipPost.getContent(),
                UserResponse.from(tipPost.getUser()),
                tipPost.getPostImages().getThumbnailImageUrl(),
                tipPost.getLikeCount(),
                tipPost.getPostComments().countComments(),
                null,
                null,
                tipPost.getCreatedAt()
        );
    }

    public static PostResponse from(NormalPost normalPost) {
        return new PostResponse(
                normalPost.getId(),
                normalPost.getPostType(),
                normalPost.getTitle(),
                normalPost.getContent(),
                UserResponse.from(normalPost.getUser()),
                normalPost.getPostImages().getThumbnailImageUrl(),
                normalPost.getLikeCount(),
                normalPost.getPostComments().countComments(),
                null,
                null,
                normalPost.getCreatedAt()

        );
    }
}
