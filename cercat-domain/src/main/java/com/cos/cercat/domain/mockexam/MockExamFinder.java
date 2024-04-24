package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockExamFinder {

    private final MockExamRepository mockExamRepository;

    public List<MockExam> find(TargetCertificate targetCertificate, Integer examYear) {
        return mockExamRepository.find(targetCertificate, examYear);
    }

    public List<Question> findQuestions(TargetMockExam targetMockExam) {
        return mockExamRepository.findQuestions(targetMockExam);
    }

    public List<Integer> findExamYears(TargetCertificate targetCertificate) {
        return mockExamRepository.findExamYears(targetCertificate);
    }
}
