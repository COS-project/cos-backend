package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.entity.CertificateExamEntity;
import com.cos.cercat.infra.entity.ExamReviewEntity;
import com.cos.cercat.domain.examreview.ExamReviewSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ExamReviewRepositoryCustom {

    Slice<ExamReviewEntity> searchExamReview(CertificateExamEntity certificateExamEntity, ExamReviewSearchCond cond, Pageable pageable);
}
