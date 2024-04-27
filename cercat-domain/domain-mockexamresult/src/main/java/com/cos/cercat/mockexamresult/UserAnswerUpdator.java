package com.cos.cercat.mockexamresult;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAnswerUpdator {

    private final MockExamResultRepository mockExamResultRepository;

    public void update(UserAnswer userAnswer) {
        mockExamResultRepository.update(userAnswer);
    }

}
