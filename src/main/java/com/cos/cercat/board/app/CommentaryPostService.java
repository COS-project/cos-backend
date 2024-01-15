package com.cos.cercat.board.app;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.board.dto.request.CommentaryPostUpdateRequest;
import com.cos.cercat.board.repository.CommentaryPostRepository;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class CommentaryPostService {

    private final CommentaryPostRepository commentaryPostRepository;

    public void createCommentaryPost(String title,
                                     String content,
                                     List<Image> images,
                                     Certificate certificate,
                                     User user,
                                     Question question) {

        CommentaryPost commentaryPost = CommentaryPost.of(title, content, user, certificate, question);
        commentaryPost.addAllPostImages(images);

        commentaryPostRepository.save(commentaryPost);
    }

    /**
     * 해설 게시글을 검색하고 페이징한다.
     *
     * @param pageable 페이징 요청 객체
     * @param certificate 게시글이 속한 자격증 엔티티
     * @param keyword 검색 키워드
     *
     * @return Slice 형태의 해설 게시글들을 반환한다.
     * */
    public Slice<CommentaryPost> searchCommentaryPosts(Pageable pageable, Certificate certificate, String keyword) {
        return commentaryPostRepository.searchPosts(pageable, certificate, keyword);
    }

    public CommentaryPost getCommentaryPost(Long postId) {
        return commentaryPostRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
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
