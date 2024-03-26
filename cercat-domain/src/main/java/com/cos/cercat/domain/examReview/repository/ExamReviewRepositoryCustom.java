package com.cos.cercat.domain.examReview.repository;

import com.cos.cercat.domain.certificate.domain.CertificateExam;
import com.cos.cercat.domain.examReview.domain.ExamReview;
import com.cos.cercat.domain.examReview.dto.ExamReviewSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ExamReviewRepositoryCustom {
    Slice<ExamReview> searchExamReview(CertificateExam certificateExam, ExamReviewSearchCond cond, Pageable pageable);
}
