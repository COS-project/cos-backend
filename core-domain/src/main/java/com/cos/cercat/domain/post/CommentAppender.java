package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.EventPublisher;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentAppender {

    private final CreatePostRepository createPostRepository;
    private final EventPublisher eventPublisher;

    public void append(User commenter, Post post, CommentContent commentContent) {
        createPostRepository.saveComment(commenter, post, commentContent);
        eventPublisher.publish(CommentCreatedEvent.from(post, commentContent));
    }
}
