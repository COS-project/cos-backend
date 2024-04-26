package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.TargetCertificate;

import java.util.List;

public interface MockExamRepository {
    Question readQuestion(TargetCertificate targetCertificate,
                          MockExamSession mockExamSession,
                          int questionSequence);

    List<MockExam> find(TargetCertificate targetCertificate, Integer examYear);

    List<Question> findQuestions(TargetMockExam targetMockExam);

    List<Integer> findExamYears(TargetCertificate targetCertificate);

    MockExam read(TargetMockExam targetMockExam);
}
