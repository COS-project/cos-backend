package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.Certificate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionReader {

    private final MockExamRepository mockExamRepository;
    private final MockExamCache mockExamCache;

    public Question read(Certificate certificate,
            MockExamSession mockExamSession,
            int questionSequence) {

        return mockExamRepository.readQuestion(certificate, mockExamSession, questionSequence);
    }

    public List<Question> read(MockExam mockExam) {
        return mockExamCache.find(mockExam)
                .orElseGet(() -> {
                    List<Question> questions = mockExamRepository.findQuestions(mockExam);
                    mockExamCache.cache(questions);
                    return questions;
                });
    }

    public Question read(Long questionId) {
        return mockExamRepository.findById(questionId);
    }

}
