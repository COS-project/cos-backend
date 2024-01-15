package com.cos.cercat.like.app;

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

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentLikeService {


    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void flipPostLike(PostComment postComment, User user) {

        CommentLikePK commentLikePK = CommentLikePK.of(user, postComment);

        if (commentLikeRepository.existsById(commentLikePK)) {
            commentLikeRepository.deleteById(commentLikePK);
            postComment.decreaseLikeCount();
            return;
        }

        commentLikeRepository.save(CommentLike.from(commentLikePK));
        postComment.increaseLikeCount();
    }
}
