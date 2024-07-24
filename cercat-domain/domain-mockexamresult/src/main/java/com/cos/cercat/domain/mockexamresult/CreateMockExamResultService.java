package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.MockExamReader;
import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.user.TargetUser;
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

    public TargetMockExamResult createMockExamResult(TargetUser targetUser,
                                                     TargetMockExam targetMockExam,
                                                     List<NewSubjectResult> newSubjectResults) {
        User user = userReader.read(targetUser);
        MockExam mockExam = mockExamReader.read(targetMockExam);
        return mockExamResultAppender.append(user, mockExam, newSubjectResults);
    }
}
