package com.cos.cercat.domain.post;

import com.cos.cercat.domain.post.event.internal.PostCommentRemovedEvent;
import com.cos.cercat.domain.post.event.internal.PostRemovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostRemover {

    private final DeletePostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void deleteRecommendTags(Post post) {
        postRepository.deleteRecommendTags(post);
    }

    public void removeImages(List<Long> removeImageIds) {
        postRepository.deleteImages(removeImageIds);
    }

    public void remove(Post post) {
        postRepository.delete(post);
        applicationEventPublisher.publishEvent(PostRemovedEvent.from(post));
    }

    public void remove(PostComment postComment) {
        postCommentRepository.deleteComment(postComment);
        applicationEventPublisher.publishEvent(PostCommentRemovedEvent.from(postComment));
    }
}
