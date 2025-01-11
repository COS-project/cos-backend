package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.Certificate;

public record NewMockExam(
        MockExamSession session,
        Long timeLimit,
        Certificate certificate
) {

    public static NewMockExam of(MockExamSession session, Long timeLimit, Certificate certificate) {
        return new NewMockExam(session, timeLimit, certificate);
    }
}
