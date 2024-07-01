package com.cos.cercat.mockexamresult;

import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.mockexam.MockExamReader;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
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
