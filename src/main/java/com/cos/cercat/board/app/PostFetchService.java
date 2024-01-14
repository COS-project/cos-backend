package com.cos.cercat.board.app;

import com.cos.cercat.board.dto.response.CommentaryPostResponse;
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

    @Transactional(readOnly = true)
    public Slice<CommentaryPostResponse> searchCommentaryPosts(Pageable pageable, Long certificateId, String keyword) {
        Certificate certificate = certificateService.getCertificate(certificateId);

        //TODO : 좋아요 수를 어떻게 할것인가?


        return null;
    }

}
