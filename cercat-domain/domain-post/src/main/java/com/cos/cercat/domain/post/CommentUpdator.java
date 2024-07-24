package com.cos.cercat.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentUpdator {

    private final UpdatePostRepository updatePostRepository;

    public void update(PostComment comment) {
        updatePostRepository.updateComment(comment);
    }
}
