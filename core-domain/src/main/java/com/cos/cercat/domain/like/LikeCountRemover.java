package com.cos.cercat.domain.like;

import com.cos.cercat.domain.post.event.internal.PostCommentRemovedEvent;
import com.cos.cercat.domain.post.event.internal.PostRemovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LikeCountRemover {

    private final LikeCountRepository likeCountRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void remove(PostRemovedEvent postRemovedEvent) {
        likeCountRepository.delete(LikeTarget.post(postRemovedEvent.postId()));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void remove(PostCommentRemovedEvent postCommentRemovedEvent) {
        likeCountRepository.delete(LikeTarget.comment(postCommentRemovedEvent.commentId()));
    }

}
