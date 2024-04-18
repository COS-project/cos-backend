package com.cos.cercat.service;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.comment.PostComment;
import com.cos.cercat.domain.CommentLike;
import com.cos.cercat.domain.EmbeddedId.CommentLikePK;
import com.cos.cercat.repository.CommentLikeRepository;
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

    public void createLike(PostComment postComment, UserEntity userEntity) {
        commentLikeRepository.save(CommentLike.of(userEntity, postComment));
        postComment.increaseLikeCount();
    }

    public void deleteLike(PostComment postComment, CommentLikePK commentLikePK) {
        commentLikeRepository.deleteById(commentLikePK);
        postComment.decreaseLikeCount();
    }
}
