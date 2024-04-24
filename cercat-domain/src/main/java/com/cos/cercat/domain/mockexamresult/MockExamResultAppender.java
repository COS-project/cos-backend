package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
