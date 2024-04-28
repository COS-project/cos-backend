package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamReader {

    private final MockExamRepository mockExamRepository;

    public Question readQuestion(Certificate certificate,
                                 MockExamSession mockExamSession,
                                 int questionSequence) {
        return mockExamRepository.readQuestion(certificate, mockExamSession, questionSequence);

    }

    public MockExam read(TargetMockExam targetMockExam) {
        return mockExamRepository.read(targetMockExam);
    }
}
