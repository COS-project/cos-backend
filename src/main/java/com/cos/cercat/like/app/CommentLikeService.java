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

import static com.cos.cercat.like.domain.EmbeddedId.QCommentLikePK.commentLikePK;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentLikeService {


    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void flipPostLike(PostComment postComment, User user) {

        CommentLikePK commentLikePK = CommentLikePK.of(user.getId(), postComment.getId());

        if (commentLikeRepository.existsById(commentLikePK)) {
            deleteCommentLike(postComment, commentLikePK);
            return;
        }

        createCommentLike(postComment, user);
    }

    private void createCommentLike(PostComment postComment, User user) {
        commentLikeRepository.save(CommentLike.of(user, postComment));
        postComment.increaseLikeCount();
    }

    private void deleteCommentLike(PostComment postComment, CommentLikePK commentLikePK) {
        commentLikeRepository.deleteById(commentLikePK);
        postComment.decreaseLikeCount();
    }
}
