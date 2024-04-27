package com.cos.cercat.examreview;

import com.cos.cercat.certificate.CertificateExam;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.user.User;

public interface ExamReviewRepository {
    void save(User user, CertificateExam recentExam, ExamReviewContent content, int prepareMonths);

    SliceResult<ExamReview> find(CertificateExam certificateExam,
                                 ExamReviewSearchCond cond,
                                 Cursor cursor);

    boolean existReview(User user, CertificateExam certificateExam);
}
