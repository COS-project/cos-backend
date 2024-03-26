package com.cos.cercat.apis.post.dto.response;

import com.cos.cercat.apis.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.apis.user.dto.response.UserResponse;
import com.cos.cercat.domain.post.*;
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
        boolean isLiked,
        Integer commentCount,
        Integer questionSequence,
        MockExamResponse mockExam,
        LocalDateTime createdAt
) {


    public static PostResponse of(Post post, boolean isLiked) {
        return switch (post.getPostType()) {
            case COMMENTARY -> of((CommentaryPost) post, isLiked);
            case TIP -> of((TipPost) post, isLiked);
            case NORMAL -> of((NormalPost) post, isLiked);
        };
    }
    private static PostResponse of(CommentaryPost commentaryPost, boolean isLiked) {
        return new PostResponse(
                commentaryPost.getId(),
                commentaryPost.getPostType(),
                commentaryPost.getTitle(),
                commentaryPost.getContent(),
                UserResponse.fromEntity(commentaryPost.getUser()),
                commentaryPost.getPostImages().getThumbnailImageUrl(),
                commentaryPost.getLikeCount(),
                isLiked,
                commentaryPost.getPostComments().countComments(),
                commentaryPost.getQuestion().getQuestionSeq(),
                MockExamResponse.from(commentaryPost.getQuestion().getMockExam()),
                commentaryPost.getCreatedAt()
        );
    }

    private static PostResponse of(TipPost tipPost, boolean isLiked) {
        return new PostResponse(
                tipPost.getId(),
                tipPost.getPostType(),
                tipPost.getTitle(),
                tipPost.getContent(),
                UserResponse.fromEntity(tipPost.getUser()),
                tipPost.getPostImages().getThumbnailImageUrl(),
                tipPost.getLikeCount(),
                isLiked,
                tipPost.getPostComments().countComments(),
                null,
                null,
                tipPost.getCreatedAt()
        );
    }

    private static PostResponse of(NormalPost normalPost, boolean isLiked) {
        return new PostResponse(
                normalPost.getId(),
                normalPost.getPostType(),
                normalPost.getTitle(),
                normalPost.getContent(),
                UserResponse.fromEntity(normalPost.getUser()),
                normalPost.getPostImages().getThumbnailImageUrl(),
                normalPost.getLikeCount(),
                isLiked,
                normalPost.getPostComments().countComments(),
                null,
                null,
                normalPost.getCreatedAt()
        );
    }

}
