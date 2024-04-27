package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamReader {

    private final MockExamRepository mockExamRepository;

    public Question readQuestion(TargetCertificate targetCertificate,
                                 MockExamSession mockExamSession,
                                 int questionSequence) {
        return mockExamRepository.readQuestion(targetCertificate, mockExamSession, questionSequence);

    }

    public MockExam read(TargetMockExam targetMockExam) {
        return mockExamRepository.read(targetMockExam);
    }
}
