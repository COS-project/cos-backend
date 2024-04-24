package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateMockExamResultService {

    private final MockExamResultAppender mockExamResultAppender;
    private final MockExamResultReader mockExamResultReader;

    public TargetMockExamResult createMockExamResult(TargetUser targetUser,
                                     TargetMockExam targetMockExam,
                                     List<NewSubjectResult> newSubjectResults) {
        int round = mockExamResultReader.count(targetUser, targetMockExam);
        return mockExamResultAppender.append(targetUser, targetMockExam, NewMockExamResult.of(round, newSubjectResults));
    }
}
