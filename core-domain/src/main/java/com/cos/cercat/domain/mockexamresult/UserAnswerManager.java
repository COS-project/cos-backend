package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAnswerManager {

    private final MockExamResultRepository mockExamResultRepository;

    public UserAnswer read(UserAnswerId userAnswerId) {
        return mockExamResultRepository.findUserAnswer(userAnswerId);
    }

    public SliceResult<UserAnswer> findAllWrongUserAnswers(User user, Certificate certificate, Cursor cursor) {
        return mockExamResultRepository.findAllWrongUserAnswers(user, certificate, cursor);
    }

    public SliceResult<UserAnswer> findWrongUserAnswers(MockExamResult mockExamResult,
                                                        Cursor cursor) {
        return mockExamResultRepository.findWrongUserAnswers(mockExamResult, cursor);
    }
}
