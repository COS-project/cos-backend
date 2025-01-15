package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.user.User;

public interface ExamReviewRepository {
    void save(User user, CertificateExam recentExam, ExamReviewContent content, int prepareMonths);

    SliceResult<ExamReview> find(CertificateExam certificateExam,
                                 ExamReviewSearchCond cond,
                                 Cursor cursor);

    boolean existReview(User user, CertificateExam certificateExam);
}
