package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.MockExamReader;
import com.cos.cercat.domain.mockexam.MockExamId;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateMockExamResultService {

    private final MockExamResultAppender mockExamResultAppender;
    private final UserReader userReader;
    private final MockExamReader mockExamReader;

    public MockExamResultId createMockExamResult(UserId userId,
                                                     MockExamId mockExamId,
                                                     List<NewSubjectResult> newSubjectResults) {
        User user = userReader.read(userId);
        MockExam mockExam = mockExamReader.read(mockExamId);
        return mockExamResultAppender.append(user, mockExam, newSubjectResults);
    }
}
