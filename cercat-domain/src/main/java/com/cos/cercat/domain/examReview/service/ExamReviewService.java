package com.cos.cercat.domain.examReview.service;

import com.cos.cercat.domain.certificate.domain.CertificateExam;
import com.cos.cercat.domain.examReview.repository.ExamReviewRepository;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.domain.examReview.domain.ExamReview;
import com.cos.cercat.domain.examReview.dto.ExamReviewSearchCond;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamReviewService {

    private final ExamReviewRepository examReviewRepository;

    public void createExamReview(ExamReview examReview) {
        if (existsExamReview(examReview.getUser(), examReview.getCertificateExam())) {
            throw new CustomException(ErrorCode.DUPLICATE_EXAM_REVIEW);
        }
        examReviewRepository.save(examReview);
    }

    public Slice<ExamReview> getExamReviews(CertificateExam certificateExam, ExamReviewSearchCond cond, Pageable pageable) {
        return examReviewRepository.searchExamReview(certificateExam, cond, pageable);
    }

    public boolean existsExamReview(User user, CertificateExam certificateExam) {
        return examReviewRepository.existsExamReviewByUserAndCertificateExam(user, certificateExam);
    }

}
