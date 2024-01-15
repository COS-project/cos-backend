package com.cos.cercat.board.app;

import com.cos.cercat.board.dto.request.PostType;
import com.cos.cercat.board.dto.response.PostResponse;
import com.cos.cercat.board.dto.response.PostWithCommentsResponse;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostFetchService {

    private final CommentaryPostService commentaryPostService;
    private final CertificateService certificateService;
    private final NormalPostService normalPostService;
    private final TipPostService tipPostService;


    @Transactional(readOnly = true)
    public Slice<PostResponse> searchPosts(Pageable pageable, PostType postType, Long certificateId, String keyword) {
        Certificate certificate = certificateService.getCertificate(certificateId);

        return switch (postType) {
            case COMMENTARY -> commentaryPostService
                    .searchCommentaryPosts(pageable, certificate, keyword)
                    .map(PostResponse::from);
            case TIP -> tipPostService
                    .searchTipPosts(pageable, certificate, keyword)
                    .map(PostResponse::from);
            case NORMAL -> normalPostService
                    .searchNormalPosts(pageable, certificate, keyword)
                    .map(PostResponse::from);
        };
    }

    @Transactional(readOnly = true)
    public PostWithCommentsResponse getPostDetail(PostType postType, Long postId) {
        return switch (postType) {
            case COMMENTARY -> PostWithCommentsResponse.from(commentaryPostService.getCommentaryPost(postId));
            case TIP -> PostWithCommentsResponse.from(tipPostService.getTipPost(postId));
            case NORMAL -> PostWithCommentsResponse.from(normalPostService.getNormalPost(postId));
        };
    }
}
