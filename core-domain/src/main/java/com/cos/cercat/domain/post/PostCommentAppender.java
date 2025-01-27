package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.EventPublisher;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCommentAppender {

    private final PostCommentRepository postCommentRepository;
    private final EventPublisher eventPublisher;

    public void append(User commenter, Post post, CommentContent commentContent) {
        PostComment postComment = PostComment.create(commenter, post, commentContent);
        postCommentRepository.save(postComment);
        eventPublisher.publish(CommentCreatedEvent.from(post, commentContent));
    }
}
