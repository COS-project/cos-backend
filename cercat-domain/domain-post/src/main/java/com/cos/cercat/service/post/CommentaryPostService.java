package com.cos.cercat.service.post;

import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.Question;
import com.cos.cercat.domain.User;
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
