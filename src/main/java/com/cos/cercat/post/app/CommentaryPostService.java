package com.cos.cercat.post.app;

import com.cos.cercat.post.domain.CommentaryPost;
import com.cos.cercat.post.dto.request.PostSearchCond;
import com.cos.cercat.post.repository.CommentaryPostRepository;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.common.Image;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.user.domain.User;
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

    public Slice<CommentaryPost> searchCommentaryPosts(Pageable pageable, Certificate certificate, PostSearchCond cond) {
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
