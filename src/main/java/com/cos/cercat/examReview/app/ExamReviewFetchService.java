package com.cos.cercat.examReview.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.app.ExamInfoService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.ExamInfo;
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
    private final ExamInfoService examInfoService;

    public Slice<ExamReviewResponse> getExamReviews(Long certificateId, ExamReviewSearchCond cond, Pageable pageable) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        ExamInfo recentExamInfo = examInfoService.getRecentExamInfo(certificate);

        return examReviewService.getExamReviews(recentExamInfo.getCertificateExam(), cond, pageable).map(ExamReviewResponse::from);
    }
}
