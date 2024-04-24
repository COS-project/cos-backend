package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamResultReader {

    private final MockExamResultRepository mockExamResultRepository;

    public int count(TargetUser targetUser, TargetMockExam targetMockExam) {
        return mockExamResultRepository.countMockExamResult(targetUser, targetMockExam);
    }

}
