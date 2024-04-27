package com.cos.cercat.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostRemover {

    private final DeletePostRepository postRepository;

    public void deleteRecommendTags(TargetPost targetPost) {
        postRepository.deleteRecommendTags(targetPost);
    }

    public void deleteImages(List<Long> removeImageIds) {
        postRepository.deleteImages(removeImageIds);
    }

    public void remove(Post post) {
        postRepository.delete(post);
    }

    public void remove(PostComment postComment) {
        postRepository.deleteComment(postComment);
    }
}
