package com.cos.cercat.apis.post.dto.response;

import com.cos.cercat.apis.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.apis.user.dto.response.UserResponse;
import com.cos.cercat.apis.comment.dto.response.PostCommentResponse;
import com.cos.cercat.apis.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.apis.post.dto.RecommendTagDTO;
import com.cos.cercat.domain.post.CommentaryPost;
import com.cos.cercat.domain.post.NormalPost;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.TipPost;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
                UserResponse.fromEntity(commentaryPost.getUserEntity()),
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
                UserResponse.fromEntity(tipPost.getUserEntity()),
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
                UserResponse.fromEntity(normalPost.getUserEntity()),
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
