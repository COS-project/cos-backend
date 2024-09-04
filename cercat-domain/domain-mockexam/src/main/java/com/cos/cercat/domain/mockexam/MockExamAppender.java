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

    public TargetMockExam append(Certificate certificate,
            MockExamSession session,
            Long timeLimit,
            List<QuestionWithSubjectSeq> contents) {
        NewMockExam newMockExam = NewMockExam.of(session, timeLimit, certificate);
        TargetMockExam targetMockExam = mockExamRepository.save(newMockExam);

        List<Subject> subjects = subjectReader.read(certificate);

        List<NewQuestion> newQuestions = contents.stream()
                .map(content -> content.toNewQuestion(subjects))
                .toList();

        mockExamRepository.saveQuestions(targetMockExam, newQuestions);
        return targetMockExam;
    }

}
