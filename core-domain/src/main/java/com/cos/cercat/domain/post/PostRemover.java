package com.cos.cercat.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostRemover {

    private final DeletePostRepository postRepository;

    public void deleteRecommendTags(Post post) {
        postRepository.deleteRecommendTags(post);
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
