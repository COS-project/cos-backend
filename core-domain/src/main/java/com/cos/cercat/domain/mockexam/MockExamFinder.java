package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.certificate.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockExamFinder {

    private final MockExamRepository mockExamRepository;

    public List<MockExam> find(Certificate certificate, Integer examYear) {
        return mockExamRepository.find(certificate, examYear);
    }

    public List<Integer> findExamYears(Certificate certificate) {
        return mockExamRepository.findExamYears(certificate);
    }
}
