package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.TargetCertificate;
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

    public List<Integer> findExamYears(TargetCertificate targetCertificate) {
        return mockExamRepository.findExamYears(targetCertificate);
    }
}
