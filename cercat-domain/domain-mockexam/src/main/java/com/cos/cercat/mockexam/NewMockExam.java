package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.Certificate;

public record NewMockExam(
        MockExamSession session,
        Long timeLimit,
        Certificate certificate
) {

    public static NewMockExam of(MockExamSession session, Long timeLimit, Certificate certificate) {
        return new NewMockExam(session, timeLimit, certificate);
    }
}
