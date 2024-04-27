package com.cos.cercat.examreview;

import com.cos.cercat.certificate.CertificateExam;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamReviewReader {

    private final ExamReviewRepository examReviewRepository;


    public boolean existReview(User user, CertificateExam certificateExam) {
        return examReviewRepository.existReview(user, certificateExam);
    }
}
