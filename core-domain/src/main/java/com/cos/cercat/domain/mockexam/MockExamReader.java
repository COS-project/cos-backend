package com.cos.cercat.domain.mockexam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamReader {

  private final MockExamRepository mockExamRepository;

  public MockExam read(MockExamId mockExamId) {
    return mockExamRepository.find(mockExamId);
  }
}
