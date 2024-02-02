package com.cos.cercat.examReview.app;

import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.certificate.domain.ExamInfo;
import com.cos.cercat.examReview.domain.ExamReview;
import com.cos.cercat.examReview.dto.request.ExamReviewSearchCond;
import com.cos.cercat.examReview.repository.ExamReviewRepository;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamReviewService {

    private final ExamReviewRepository examReviewRepository;

    public void createExamReview(ExamReview examReview) {
        if (examReviewRepository.existsExamReviewByUserAndCertificateExam(examReview.getUser(), examReview.getCertificateExam())) {
            throw new CustomException(ErrorCode.DUPLICATE_EXAM_REVIEW);
        }
        examReviewRepository.save(examReview);
    }

    public Slice<ExamReview> getExamReviews(CertificateExam certificateExam, ExamReviewSearchCond cond, Pageable pageable) {
        return examReviewRepository.searchExamReview(certificateExam, cond, pageable);
    }

}
