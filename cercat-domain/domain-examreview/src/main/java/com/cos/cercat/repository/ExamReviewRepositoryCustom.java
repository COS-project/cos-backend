package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateExam;
import com.cos.cercat.domain.ExamReview;
import com.cos.cercat.dto.ExamReviewSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ExamReviewRepositoryCustom {
    Slice<ExamReview> searchExamReview(CertificateExam certificateExam, ExamReviewSearchCond cond, Pageable pageable);
}
