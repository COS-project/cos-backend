package com.cos.cercat.mockexam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamReader {

    private final MockExamRepository mockExamRepository;

    public MockExam read(TargetMockExam targetMockExam) {
        return mockExamRepository.read(targetMockExam);
    }
}
