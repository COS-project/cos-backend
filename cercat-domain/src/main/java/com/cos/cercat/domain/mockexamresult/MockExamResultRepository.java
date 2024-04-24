package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.user.TargetUser;

public interface MockExamResultRepository {

    TargetMockExamResult save(TargetUser targetUser, TargetMockExam targetMockExam, NewMockExamResult newMockExamResult);

    int countMockExamResult(TargetUser targetUser, TargetMockExam targetMockExam);

    void updateUserAnswer(UserAnswer userAnswer);

    UserAnswer find(TargetUserAnswer targetUserAnswer);
}
