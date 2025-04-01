package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.certificate.exception.CertificateException;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.examreview.exception.ExamReviewException;
import com.cos.cercat.domain.user.User;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamReviewReader {

    private final ExamReviewRepository examReviewRepository;

    public SliceResult<ExamReview> read(CertificateExam certificateExam, ExamReviewSearchCond cond, Cursor cursor) {
        return examReviewRepository.find(certificateExam, cond, cursor);
    }

    public boolean validate(User user, CertificateExam certificateExam) {
        LocalDateTime examDateTime = certificateExam.examInformation().examSchedule().examDateTime();
        LocalDateTime twoWeeksAfterExam = examDateTime.plusWeeks(2);
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(twoWeeksAfterExam)) {
            throw ExamReviewException.reviewPeriodExpired();
        }

        return examReviewRepository.existReview(user, certificateExam);
    }
}
