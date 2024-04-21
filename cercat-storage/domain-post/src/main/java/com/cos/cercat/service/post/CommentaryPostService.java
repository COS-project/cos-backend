package com.cos.cercat.service.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.CommentaryPostEntity;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.post.CommentaryPostSearchCond;
import com.cos.cercat.repository.post.CommentaryPostJpaRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class CommentaryPostService {

    private final CommentaryPostJpaRepository commentaryPostJpaRepository;

    public void createCommentaryPost(CommentaryPostEntity commentaryPost) {

        commentaryPostJpaRepository.save(commentaryPost);
    }

    public Slice<CommentaryPostEntity> searchCommentaryPosts(Pageable pageable, CertificateEntity certificateEntity, CommentaryPostSearchCond cond) {
        return commentaryPostJpaRepository.searchPosts(pageable, certificateEntity, cond);
    }

    public CommentaryPostEntity getCommentaryPost(Long postId) {
        return commentaryPostJpaRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Slice<CommentaryPostEntity> getMyCommentaryPosts(UserEntity userEntity, Pageable pageable) {
        return commentaryPostJpaRepository.findCommentaryPostsByUserEntity(userEntity, pageable);
    }

    public void updateCommentaryPost(Long postId,
                                     String title,
                                     String content,
                                     QuestionEntity questionEntity,
                                     List<Image> images,
                                     UserEntity userEntity) {
        CommentaryPostEntity commentaryPost = getCommentaryPost(postId);

        if (commentaryPost.isAuthorized(userEntity)) {
            commentaryPost.updatePostInfo(title, content, images);
            commentaryPost.updateQuestion(questionEntity);
            return;
        }

        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }

}
