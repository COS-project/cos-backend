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

import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class CommentaryPostService {

    private final CommentaryPostRepository commentaryPostRepository;

    /**
     * 해설 게시글을 생성한다.
     *
     * @param certificate 게시글이 속한 자격증 엔티티
     * @param title 게시글 제목
     * @param content 게시글 내용
     * @param user 로그인한 유저 엔티티
     * @param images 게시글에 포함된 이미지 엔티티 리스트
     * @param question 해설 게시글의 문제 엔티티
     * */
    @Builder(builderMethodName = "createCommentaryPostBuilder")
    public void createCommentaryPost(Certificate certificate,
                                     String title,
                                     String content,
                                     User user,
                                     List<Image> images,
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
        return commentaryPostRepository.searchCommentaryPosts(pageable, certificate, keyword);
    }

    /***
     * 게시글 ID를 통해 게시글 엔티티를 조회한다.
     *
     * @param postId 게시글 엔티티 고유 ID
     * @return 해설 게시글 엔티티를 반환한다.
     */
    public CommentaryPost getCommentaryPost(Long postId) {
        return commentaryPostRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    @Builder(builderMethodName = "updateCommentaryPostBuilder")
    public void updateCommentaryPost(Long postId,
                                     String title,
                                     String content,
                                     Question question,
                                     List<Image> images
                                     ) {
        //TODO: 업데이트 구현 - 이미지 수정, 모의고사, 문제 수정
        CommentaryPost commentaryPost = getCommentaryPost(postId);

        commentaryPost.update(title, content, question);
        commentaryPost.addAllPostImages(images);
    }

}
