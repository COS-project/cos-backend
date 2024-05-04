package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionReader {

    private final MockExamRepository mockExamRepository;

    public Question read(Certificate certificate,
                                 MockExamSession mockExamSession,
                                 int questionSequence) {

        return mockExamRepository.readQuestion(certificate, mockExamSession, questionSequence);
    }

}
