package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MockExamService {

    private final MockExamFinder mockExamFinder;
    private final QuestionFinder questionFinder;

    public List<MockExam> find(TargetCertificate targetCertificate,
                               Integer examYear) {
        return mockExamFinder.find(targetCertificate, examYear);
    }

    public List<Question> findQuestions(TargetMockExam targetMockExam) {
        return questionFinder.find(targetMockExam);
    }

    public List<Integer> findExamYears(TargetCertificate targetCertificate) {
        return mockExamFinder.findExamYears(targetCertificate);
    }
}
