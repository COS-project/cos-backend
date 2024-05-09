package com.cos.cercat.mockexamresult;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAnswerManager {

    private final MockExamResultRepository mockExamResultRepository;

    public UserAnswer read(TargetUserAnswer targetUserAnswer) {
        return mockExamResultRepository.findUserAnswer(targetUserAnswer);
    }

    public SliceResult<UserAnswer> findAllWrongUserAnswers(User user, Certificate certificate, Cursor cursor) {
        return mockExamResultRepository.findAllWrongUserAnswers(user, certificate, cursor);
    }

    public SliceResult<UserAnswer> findWrongUserAnswers(MockExamResult mockExamResult,
                                                        Cursor cursor) {
        return mockExamResultRepository.findWrongUserAnswers(mockExamResult, cursor);
    }
}
