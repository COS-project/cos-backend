package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamReviewReader {

    private final ExamReviewRepository examReviewRepository;

    public SliceResult<ExamReview> read(CertificateExam certificateExam, ExamReviewSearchCond cond, Cursor cursor) {
        return examReviewRepository.find(certificateExam, cond, cursor);
    }

    public boolean exists(User user, CertificateExam certificateExam) {
        return examReviewRepository.existReview(user, certificateExam);
    }
}
