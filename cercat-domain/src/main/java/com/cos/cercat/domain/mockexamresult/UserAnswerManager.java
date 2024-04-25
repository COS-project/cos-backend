package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAnswerManager {

    private final MockExamResultRepository mockExamResultRepository;

    public UserAnswer read(TargetUserAnswer targetUserAnswer) {
        return mockExamResultRepository.findUserAnswer(targetUserAnswer);
    }

    public SliceResult<UserAnswer> findAllWrongUserAnswers(TargetCertificate targetCertificate, TargetUser targetUser, Cursor cursor) {
        return mockExamResultRepository.findAllWrongUserAnswers(targetCertificate, targetUser, cursor);
    }

    public SliceResult<UserAnswer> findWrongUserAnswers(TargetMockExamResult targetMockExamResult,
                                                        TargetUser targetUser,
                                                        Cursor cursor) {
        return mockExamResultRepository.findWrongUserAnswers(targetMockExamResult, targetUser, cursor);
    }
}
