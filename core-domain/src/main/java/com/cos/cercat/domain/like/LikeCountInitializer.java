package com.cos.cercat.domain.like;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

import com.cos.cercat.domain.post.event.external.CommentCreatedEvent;
import com.cos.cercat.domain.post.event.internal.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeCountInitializer {

    private final LikeCountRepository likeCountRepository;

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void init(PostCreatedEvent postCreatedEvent) {
        log.debug("Initializing like count for post: {}", postCreatedEvent.postId());
        LikeCount likeCount = LikeCount.init(LikeTarget.post(postCreatedEvent.postId().value()));
        likeCountRepository.save(likeCount);
    }

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void init(CommentCreatedEvent commentCreatedEvent) {
        log.debug("Initializing like count for comment: {}", commentCreatedEvent.commentId());
        LikeCount likeCount = LikeCount.init(LikeTarget.comment(commentCreatedEvent.commentId()));
        likeCountRepository.save(likeCount);
    }

}
