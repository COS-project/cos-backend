package com.cos.cercat.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostUpdator {

    private final UpdatePostRepository postRepository;
    private final PostRemover postRemover;

    public void updateComment(PostComment comment) {
        postRepository.updateComment(comment);
    }

    public void update(Post post) {
        postRepository.update(post);
    }

    @Transactional
    public void update(Post post, List<Long> removeImageIds) {
        postRepository.update(post);
        postRemover.deleteImages(removeImageIds);
    }

    @Transactional
    public void update(TipPost post, List<Long> removeImageIds) {
        postRemover.deleteRecommendTags(TargetPost.from(post.getId()));
        postRepository.updateTipPost(post);
        postRemover.deleteImages(removeImageIds);

    }
}
