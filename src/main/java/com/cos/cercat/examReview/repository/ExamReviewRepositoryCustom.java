package com.cos.cercat.examReview.repository;

import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.examReview.domain.ExamReview;
import com.cos.cercat.examReview.dto.request.ExamReviewSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ExamReviewRepositoryCustom {
    Slice<ExamReview> searchExamReview(CertificateExam certificateExam, ExamReviewSearchCond cond, Pageable pageable);
}
