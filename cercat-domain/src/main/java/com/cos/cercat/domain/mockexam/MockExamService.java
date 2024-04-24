package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MockExamService {

    private final MockExamFinder mockExamFinder;

    public List<MockExam> find(TargetCertificate targetCertificate,
                               Integer examYear) {
        return mockExamFinder.find(targetCertificate, examYear);
    }

    //TODO: Cache
    public List<Question> findQuestions(TargetMockExam targetMockExam) {
        return mockExamFinder.findQuestions(targetMockExam);
    }

    public List<Integer> findExamYears(TargetCertificate targetCertificate) {
        return mockExamFinder.findExamYears(targetCertificate);
    }
}
