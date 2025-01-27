package com.cos.cercat.domain.post;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record PostWithComments(
        Post post,
        List<PostComment> postComments
) {
    public static PostWithComments of(Post post, List<PostComment> postComments) {
        return new PostWithComments(post, postComments).organizeChildComments();
    }

    public PostWithComments organizeChildComments() {
        Map<Long, PostComment> map = postComments.stream()
                .collect(Collectors.toMap(PostComment::getId, Function.identity()));

        map.values().stream()
                .filter(PostComment::hasParent)
                .forEach(postComment -> {
                    Long parentId = postComment.getContent().parentId();
                    PostComment parentComment = map.get(parentId);
                    parentComment.addChildren(postComment);
                });

        List<PostComment> collected = map.values().stream()
                .filter(postComment -> !postComment.hasParent())
                .sorted(Comparator
                        .comparing(PostComment::getDateTime, Comparator.comparing(DateTime::createdAt).reversed())
                        .thenComparing(PostComment::getId))
                .toList();

        return new PostWithComments(post, collected);
    }
}
