package com.cos.cercat.board.app;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.board.dto.response.CommentaryPostResponse;
import com.cos.cercat.board.dto.response.CommentaryPostWithCommentsResponse;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostFetchService {

    private final CommentaryPostService commentaryPostService;
    private final CertificateService certificateService;

    /***
     * 키워드를 통해 해설 게시글을 검색한다.
     *
     * @param pageable 페이징 요청 객체
     * @param certificateId 자격증 엔티티 고유 ID
     * @param keyword 검색 키워드
     * @return Slice 형태의 해설 게시글 응답 DTO
     */
    @Transactional(readOnly = true)
    public Slice<CommentaryPostResponse> searchCommentaryPosts(Pageable pageable, Long certificateId, String keyword) {
        Certificate certificate = certificateService.getCertificate(certificateId);

        return commentaryPostService
                .searchCommentaryPosts(pageable, certificate, keyword)
                .map(CommentaryPostResponse::from);

    }

    /***
     * 댓글을 포함하여 해설 게시글 상세를 조회한다..
     *
     * @param postId 게시글 엔티티 고유 ID
     * @return 댓글을 포함한 게시글 상세 정보
     */
    @Transactional(readOnly = true)
    public CommentaryPostWithCommentsResponse getCommentaryPostDetail(Long postId) {
        return CommentaryPostWithCommentsResponse.from(commentaryPostService.getCommentaryPost(postId));
    }
}
