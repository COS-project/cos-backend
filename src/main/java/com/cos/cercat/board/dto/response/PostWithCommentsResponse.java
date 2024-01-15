package com.cos.cercat.board.dto.response;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.comment.dto.response.PostCommentResponse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record PostWithCommentsResponse(
        PostResponse postInfo,
        List<PostCommentResponse> postComments
) {

    public static PostWithCommentsResponse from(Post post) {
        return new PostWithCommentsResponse(
                PostResponse.from(post),
                organizeChildComments(post.getPostComments().getPostComments())
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
