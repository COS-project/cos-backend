package com.cos.cercat.service.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.Question;
import com.cos.cercat.domain.post.CommentaryPost;
import com.cos.cercat.dto.CommentaryPostSearchCond;
import com.cos.cercat.repository.post.CommentaryPostRepository;
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

    private final CommentaryPostRepository commentaryPostRepository;

    public void createCommentaryPost(CommentaryPost commentaryPost) {

        commentaryPostRepository.save(commentaryPost);
    }

    public Slice<CommentaryPost> searchCommentaryPosts(Pageable pageable, CertificateEntity certificateEntity, CommentaryPostSearchCond cond) {
        return commentaryPostRepository.searchPosts(pageable, certificateEntity, cond);
    }

    public CommentaryPost getCommentaryPost(Long postId) {
        return commentaryPostRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Slice<CommentaryPost> getMyCommentaryPosts(UserEntity userEntity, Pageable pageable) {
        return commentaryPostRepository.findCommentaryPostsByUserEntity(userEntity, pageable);
    }

    public void updateCommentaryPost(Long postId,
                                     String title,
                                     String content,
                                     Question question,
                                     List<Image> images,
                                     UserEntity userEntity) {
        CommentaryPost commentaryPost = getCommentaryPost(postId);

        if (commentaryPost.isAuthorized(userEntity)) {
            commentaryPost.updatePostInfo(title, content, images);
            commentaryPost.updateQuestion(question);
            return;
        }

        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }

}
