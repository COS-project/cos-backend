package com.cos.cercat.mockexam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionFinder {

    private final MockExamRepository mockExamRepository;
    private final MockExamCacheRepository mockExamCacheRepository;

    public List<Question> find(TargetMockExam targetMockExam) {
        return mockExamCacheRepository.getQuestions(targetMockExam)
                .orElseGet(() -> {
                    List<Question> questions = mockExamRepository.findQuestions(targetMockExam);
                    mockExamCacheRepository.setQuestions(questions);
                    return questions;
                });
    }

}
