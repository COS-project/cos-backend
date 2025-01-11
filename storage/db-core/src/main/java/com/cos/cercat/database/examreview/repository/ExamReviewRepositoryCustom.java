package com.cos.cercat.database.examreview.repository;

import com.cos.cercat.database.certificate.entity.CertificateExamEntity;
import com.cos.cercat.database.examreview.entity.ExamReviewEntity;
import com.cos.cercat.domain.examreview.ExamReviewSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ExamReviewRepositoryCustom {

    Slice<ExamReviewEntity> searchExamReview(CertificateExamEntity certificateExamEntity, ExamReviewSearchCond cond, Pageable pageable);
}
