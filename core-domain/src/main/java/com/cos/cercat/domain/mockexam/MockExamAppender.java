package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.Subject;
import com.cos.cercat.domain.certificate.SubjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockExamAppender {

    private final MockExamRepository mockExamRepository;
    private final SubjectReader subjectReader;

    public MockExamId append(
            Certificate certificate,
            MockExamInfo mockExamInfo,
            List<QuestionWithSubjectSeq> questions
    ) {
        NewMockExam newMockExam = NewMockExam.of(mockExamInfo, certificate);
        MockExamId mockExamId = mockExamRepository.save(newMockExam);

        List<Subject> subjects = subjectReader.read(certificate);

        List<NewQuestion> newQuestions = questions.stream()
                .map(content -> content.toNewQuestion(subjects))
                .toList();

        mockExamRepository.saveQuestions(mockExamId, newQuestions);
        return mockExamId;
    }

    private int calculateTotalScore(List<QuestionWithSubjectSeq> contents) {
        return contents.stream()
                .mapToInt(QuestionWithSubjectSeq::getScore)
                .sum();
    }

}
