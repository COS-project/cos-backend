package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamReviewAppender {

    private final ExamReviewRepository examReviewRepository;


    public void append(User user,
                       CertificateExam recentExam,
                       ExamReviewContent content,
                       int prepareMonths) {
        examReviewRepository.save(user, recentExam, content, prepareMonths);

    }
}
