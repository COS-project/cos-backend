package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.certificate.CertificateExam;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ExamReviewPeriodChecker {

    public boolean check(CertificateExam certificateExam) {
        LocalDateTime examDateTime = certificateExam.examInformation().examSchedule().examDateTime();
        LocalDateTime twoWeeksAfterExam = examDateTime.plusWeeks(2);
        LocalDateTime now = LocalDateTime.now();

        return !now.isAfter(twoWeeksAfterExam);
    }

}
