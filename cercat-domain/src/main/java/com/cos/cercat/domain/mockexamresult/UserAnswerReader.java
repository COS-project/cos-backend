package com.cos.cercat.domain.mockexamresult;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAnswerReader {

    private final MockExamResultRepository mockExamResultRepository;

    public UserAnswer read(TargetUserAnswer targetUserAnswer) {
        return mockExamResultRepository.find(targetUserAnswer);
    }

}
