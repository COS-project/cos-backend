package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateExam;
import com.cos.cercat.domain.User;
import com.cos.cercat.repository.ExamReviewRepository;
import com.cos.cercat.domain.ExamReview;
import com.cos.cercat.dto.ExamReviewSearchCond;
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
