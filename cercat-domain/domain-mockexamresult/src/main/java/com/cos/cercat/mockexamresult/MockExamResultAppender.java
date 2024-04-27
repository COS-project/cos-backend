package com.cos.cercat.mockexamresult;

import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamResultAppender {

    private final MockExamResultRepository mockExamResultRepository;

    public TargetMockExamResult append(TargetUser targetUser,
                                       TargetMockExam targetMockExam,
                                       NewMockExamResult newSubjectResults) {
        return mockExamResultRepository.save(
                targetUser,
                targetMockExam,
                newSubjectResults
        );
    }
}
