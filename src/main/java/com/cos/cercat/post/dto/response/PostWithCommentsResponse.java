package com.cos.cercat.post.dto.response;

import com.cos.cercat.post.domain.*;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.comment.dto.response.PostCommentResponse;
import com.cos.cercat.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.post.dto.RecommendTagDTO;
import com.cos.cercat.user.dto.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostWithCommentsResponse(
        Long postId,
        String title,
        String content,
        UserResponse user,
        List<String> postImages,
        Integer likeCount,
        boolean isLiked,
        Integer commentCount,
        Set<RecommendTagDTO> recommendTags,
        QuestionResponse question,
        MockExamResponse mockExam,
        LocalDateTime createdAt,
        List<PostCommentResponse> postComments
) {

    public static PostWithCommentsResponse of(Post post, List<PostCommentResponse> postComments, boolean isLiked) {
        return switch (post.getPostType()) {
            case COMMENTARY -> of((CommentaryPost) post, postComments, isLiked);
            case TIP -> of((TipPost) post, postComments, isLiked);
            case NORMAL -> of((NormalPost) post, postComments, isLiked);
        };
    }

    private static PostWithCommentsResponse of(CommentaryPost commentaryPost, List<PostCommentResponse> postComments, boolean isLiked) {
        return new PostWithCommentsResponse(
                commentaryPost.getId(),
                commentaryPost.getTitle(),
                commentaryPost.getContent(),
                UserResponse.fromEntity(commentaryPost.getUser()),
                commentaryPost.getPostImages().getImageUrls(),
                commentaryPost.getLikeCount(),
                isLiked,
                commentaryPost.getPostComments().countComments(),
                null,
                QuestionResponse.from(commentaryPost.getQuestion()),
                MockExamResponse.from(commentaryPost.getQuestion().getMockExam()),
                commentaryPost.getCreatedAt(),
                postComments
        );
    }

    private static PostWithCommentsResponse of(TipPost tipPost, List<PostCommentResponse> postComments, boolean isLiked) {
        return new PostWithCommentsResponse(
                tipPost.getId(),
                tipPost.getTitle(),
                tipPost.getContent(),
                UserResponse.fromEntity(tipPost.getUser()),
                tipPost.getPostImages().getImageUrls(),
                tipPost.getLikeCount(),
                isLiked,
                tipPost.getPostComments().countComments(),
                tipPost.getRecommendTags().getAll().stream()
                        .map(RecommendTagDTO::from)
                        .collect(Collectors.toSet()),
                null,
                null,
                tipPost.getCreatedAt(),
                postComments
        );
    }

    private static PostWithCommentsResponse of(NormalPost normalPost, List<PostCommentResponse> postComments, boolean isLiked) {
        return new PostWithCommentsResponse(
                normalPost.getId(),
                normalPost.getTitle(),
                normalPost.getContent(),
                UserResponse.fromEntity(normalPost.getUser()),
                normalPost.getPostImages().getImageUrls(),
                normalPost.getLikeCount(),
                isLiked,
                normalPost.getPostComments().countComments(),
                null,
                null,
                null,
                normalPost.getCreatedAt(),
                postComments
        );
    }


}
