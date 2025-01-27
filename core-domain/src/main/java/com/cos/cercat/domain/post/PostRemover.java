package com.cos.cercat.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostRemover {

    private final DeletePostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    public void deleteRecommendTags(Post post) {
        postRepository.deleteRecommendTags(post);
    }

    public void removeImages(List<Long> removeImageIds) {
        postRepository.deleteImages(removeImageIds);
    }

    public void remove(Post post) {
        postRepository.delete(post);
    }

    public void remove(PostComment postComment) {
        postCommentRepository.deleteComment(postComment);
    }
}
