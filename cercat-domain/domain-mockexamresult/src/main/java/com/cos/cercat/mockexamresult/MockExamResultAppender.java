package com.cos.cercat.mockexamresult;

import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockExamResultAppender {

    private final MockExamResultRepository mockExamResultRepository;
    private final MockExamResultReader mockExamResultReader;

    public TargetMockExamResult append(User user,
                                       MockExam mockExam,
                                       List<NewSubjectResult> newSubjectResults) {

        int round = mockExamResultReader.count(user, mockExam); // 응시 횟수
        return mockExamResultRepository.save(
                user,
                mockExam,
                NewMockExamResult.of(round, newSubjectResults)
        );
    }
}
