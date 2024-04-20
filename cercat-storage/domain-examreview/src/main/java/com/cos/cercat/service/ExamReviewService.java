package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateExamEntity;
import com.cos.cercat.domain.UserEntity;
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
        if (existsExamReview(examReview.getUserEntity(), examReview.getCertificateExamEntity())) {
            throw new CustomException(ErrorCode.DUPLICATE_EXAM_REVIEW);
        }
        examReviewRepository.save(examReview);
    }

    public Slice<ExamReview> getExamReviews(CertificateExamEntity certificateExamEntity, ExamReviewSearchCond cond, Pageable pageable) {
        return examReviewRepository.searchExamReview(certificateExamEntity, cond, pageable);
    }

    public boolean existsExamReview(UserEntity userEntity, CertificateExamEntity certificateExamEntity) {
        return examReviewRepository.existsExamReviewByUserEntityAndCertificateExamEntity(userEntity, certificateExamEntity);
    }

}
