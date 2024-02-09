package com.cos.cercat.examReview.app;

import com.cos.cercat.certificate.app.CertificateExamService;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.examReview.dto.request.ExamReviewSearchCond;
import com.cos.cercat.examReview.dto.response.ExamReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamReviewFetchService {

    private final ExamReviewService examReviewService;
    private final CertificateService certificateService;
    private final CertificateExamService certificateExamService;

    /***
     * 최근 자격증시험의 따끈 후기를 조회합니다.
     * @param certificateId 자격증 ID
     * @param cond 검색 필터
     * @param pageable 페이징 정보
     * @return 슬라이스 형태의 따끈후기 Response DTO
     */
    public Slice<ExamReviewResponse> getExamReviews(Long certificateId, ExamReviewSearchCond cond, Pageable pageable) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        CertificateExam recentCertificateExam = certificateExamService.getRecentCertificateExam(certificate);

        log.info("certificate - {}, examYear - {}, round - {} 따끈 후기 조회",
                certificate.getCertificateName(), recentCertificateExam.getExamYear(), recentCertificateExam.getRound());
        return examReviewService.getExamReviews(recentCertificateExam, cond, pageable).map(ExamReviewResponse::from);
    }
}
