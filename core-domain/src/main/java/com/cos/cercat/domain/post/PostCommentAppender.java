package com.cos.cercat.domain.post;

import com.cos.cercat.domain.post.event.external.CommentCreatedEvent;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostCommentAppender {

    private final PostCommentRepository postCommentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void append(User commenter, Post post, CommentContent commentContent) {
        PostComment postComment = PostComment.create(commenter, post, commentContent);
        PostComment saved = postCommentRepository.save(postComment);
        applicationEventPublisher.publishEvent(CommentCreatedEvent.from(post, saved));
    }
}
