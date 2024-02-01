package com.cos.cercat.like.app;

import com.cos.cercat.alarm.app.producer.AlarmProducer;
import com.cos.cercat.alarm.domain.AlarmType;
import com.cos.cercat.alarm.dto.AlarmArg;
import com.cos.cercat.alarm.dto.AlarmEvent;
import com.cos.cercat.comment.app.PostCommentService;
import com.cos.cercat.comment.domain.PostComment;
import com.cos.cercat.like.domain.CommentLike;
import com.cos.cercat.like.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.like.repository.CommentLikeRepository;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cos.cercat.like.domain.EmbeddedId.QCommentLikePK.commentLikePK;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    public boolean existsLike(CommentLikePK commentLikePK) {
        return commentLikeRepository.existsById(commentLikePK);
    }

    public void createLike(PostComment postComment, User user) {
        commentLikeRepository.save(CommentLike.of(user, postComment));
        postComment.increaseLikeCount();
    }

    public void deleteLike(PostComment postComment, CommentLikePK commentLikePK) {
        commentLikeRepository.deleteById(commentLikePK);
        postComment.decreaseLikeCount();
    }
}
