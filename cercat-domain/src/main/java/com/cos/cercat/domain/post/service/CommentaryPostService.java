package com.cos.cercat.domain.post.service;

import com.cos.cercat.domain.common.domain.Image;
import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.mockExam.domain.Question;
import com.cos.cercat.domain.post.domain.CommentaryPost;
import com.cos.cercat.domain.post.dto.CommentaryPostSearchCond;
import com.cos.cercat.domain.post.repository.CommentaryPostRepository;
import com.cos.cercat.domain.user.domain.User;
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

    public Slice<CommentaryPost> searchCommentaryPosts(Pageable pageable, Certificate certificate, CommentaryPostSearchCond cond) {
        return commentaryPostRepository.searchPosts(pageable, certificate, cond);
    }

    public CommentaryPost getCommentaryPost(Long postId) {
        return commentaryPostRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Slice<CommentaryPost> getMyCommentaryPosts(User user, Pageable pageable) {
        return commentaryPostRepository.findCommentaryPostsByUser(user, pageable);
    }

    public void updateCommentaryPost(Long postId,
                                     String title,
                                     String content,
                                     Question question,
                                     List<Image> images,
                                     User user) {
        CommentaryPost commentaryPost = getCommentaryPost(postId);

        if (commentaryPost.isAuthorized(user)) {
            commentaryPost.updatePostInfo(title, content, images);
            commentaryPost.updateQuestion(question);
            return;
        }

        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }

}
