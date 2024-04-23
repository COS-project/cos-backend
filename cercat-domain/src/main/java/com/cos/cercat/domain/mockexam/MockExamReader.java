package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamReader {

    private final MockExamRepository mockExamRepository;

    public Question readQuestion(TargetCertificate targetCertificate,
                                 MockExamSession mockExamSession,
                                 int questionSequence) {
        return mockExamRepository.findQuestion(targetCertificate, mockExamSession, questionSequence);

    }
}
