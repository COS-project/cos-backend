package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.Certificate;
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
