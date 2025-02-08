package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.domain.certificate.CertificateExamReader;
import com.cos.cercat.domain.learning.Goal;
import com.cos.cercat.domain.learning.GoalReader;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamReviewAppender {

    private final ExamReviewRepository examReviewRepository;
    private final CertificateExamReader certificateExamReader;
    private final GoalReader goalReader;


    public void append(User user,
                       Certificate certificate,
                       ExamReviewContent content) {
        CertificateExam recentExam = certificateExamReader.readPreviousExam(certificate);
        Goal goal = goalReader.readRecentGoal(user, certificate);
        examReviewRepository.save(user, recentExam, content, goal.getPrepareMonths());
    }
}
