package com.cos.cercat.domain.mockexam;


import com.cos.cercat.domain.certificate.Certificate;

public record MockExam(
        long id,
        MockExamSession mockExamSession,
        long timeLimit,
        Certificate certificate
) {
    public static MockExam of(TargetMockExam targetMockExam, NewMockExam newMockExam) {
        return new MockExam(targetMockExam.mockExamId(), newMockExam.session(), newMockExam.timeLimit(), newMockExam.certificate());
    }
}
