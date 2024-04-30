package com.cos.cercat.mockexamresult;

import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockExamResultAppender {

    private final MockExamResultRepository mockExamResultRepository;

    public TargetMockExamResult append(User user,
                                       MockExam mockExam,
                                       NewMockExamResult newSubjectResults) {
        return mockExamResultRepository.save(
                user,
                mockExam,
                newSubjectResults
        );
    }
}
