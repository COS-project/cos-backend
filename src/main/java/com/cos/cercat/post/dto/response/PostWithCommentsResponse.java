package com.cos.cercat.post.dto.response;

import com.cos.cercat.post.domain.CommentaryPost;
import com.cos.cercat.post.domain.NormalPost;
import com.cos.cercat.post.domain.Post;
import com.cos.cercat.post.domain.TipPost;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.comment.dto.response.PostCommentResponse;
import com.cos.cercat.mockExam.dto.response.MockExamResponse;
import com.cos.cercat.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.user.dto.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        Integer commentCount,
        QuestionResponse question,
        MockExamResponse mockExam,
        LocalDateTime createdAt,
        List<PostCommentResponse> postComments
) {

    public static PostWithCommentsResponse from(Post post) {
        return switch (post.getPostType()) {
            case COMMENTARY -> from((CommentaryPost) post);
            case TIP -> from((TipPost) post);
            case NORMAL -> from((NormalPost) post);
        };
    }

    private static PostWithCommentsResponse from(CommentaryPost commentaryPost) {
        return new PostWithCommentsResponse(
                commentaryPost.getId(),
                commentaryPost.getTitle(),
                commentaryPost.getContent(),
                UserResponse.fromEntity(commentaryPost.getUser()),
                commentaryPost.getPostImages().getImageUrls(),
                commentaryPost.getLikeCount(),
                commentaryPost.getPostComments().countComments(),
                QuestionResponse.from(commentaryPost.getQuestion()),
                MockExamResponse.from(commentaryPost.getQuestion().getMockExam()),
                commentaryPost.getCreatedAt(),
                organizeChildComments(commentaryPost.getPostComments().getAll())
        );
    }

    private static PostWithCommentsResponse from(TipPost tipPost) {
        return new PostWithCommentsResponse(
                tipPost.getId(),
                tipPost.getTitle(),
                tipPost.getContent(),
                UserResponse.fromEntity(tipPost.getUser()),
                tipPost.getPostImages().getImageUrls(),
                tipPost.getLikeCount(),
                tipPost.getPostComments().countComments(),
                null,
                null,
                tipPost.getCreatedAt(),
                organizeChildComments(tipPost.getPostComments().getAll())
        );
    }

    private static PostWithCommentsResponse from(NormalPost normalPost) {
        return new PostWithCommentsResponse(
                normalPost.getId(),
                normalPost.getTitle(),
                normalPost.getContent(),
                UserResponse.fromEntity(normalPost.getUser()),
                normalPost.getPostImages().getImageUrls(),
                normalPost.getLikeCount(),
                normalPost.getPostComments().countComments(),
                null,
                null,
                normalPost.getCreatedAt(),
                organizeChildComments(normalPost.getPostComments().getAll())
        );
    }

    private static List<PostCommentResponse> organizeChildComments(List<PostComment> postComments) {
        Map<Long, PostCommentResponse> map = postComments.stream()
                .map(PostCommentResponse::from)
                .collect(Collectors.toMap(PostCommentResponse::postCommentId, Function.identity()));

        map.values().stream()
                .filter(PostCommentResponse::hasParentComment)
                .forEach(postComment -> {
                    PostCommentResponse parentComment = map.get(postComment.parentCommentId());
                    parentComment.addChildComment(postComment);
                });

        return map.values().stream()
                .filter(postComment -> !postComment.hasParentComment())
                .sorted(Comparator
                        .comparing(PostCommentResponse::createdAt)
                        .reversed()
                        .thenComparing(PostCommentResponse::postCommentId))
                .collect(Collectors.toList());
    }
}
