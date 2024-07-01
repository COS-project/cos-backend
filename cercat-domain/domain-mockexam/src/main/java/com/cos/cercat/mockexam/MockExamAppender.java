package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.SubjectFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockExamAppender {

    private final MockExamRepository mockExamRepository;
    private final SubjectFinder subjectFinder;

    public TargetMockExam append(Certificate certificate,
                                 MockExamSession session,
                                 Long timeLimit,
                                 List<NewQuestion> newQuestions) {
        NewMockExam newMockExam = NewMockExam.of(session, timeLimit, certificate);
        TargetMockExam targetMockExam = mockExamRepository.save(newMockExam);

        mockExamRepository.saveQuestions(MockExam.of(targetMockExam, newMockExam), newQuestions);
        return null;
    }

}
