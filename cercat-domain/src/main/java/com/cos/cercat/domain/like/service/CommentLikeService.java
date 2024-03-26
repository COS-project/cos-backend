package com.cos.cercat.domain.like.service;

import com.cos.cercat.domain.comment.domain.PostComment;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.domain.like.domain.CommentLike;
import com.cos.cercat.domain.like.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.domain.like.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    public boolean existsLike(CommentLikePK commentLikePK) {
        return commentLikeRepository.existsCommentLikeByCommentLikePK(commentLikePK);
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
