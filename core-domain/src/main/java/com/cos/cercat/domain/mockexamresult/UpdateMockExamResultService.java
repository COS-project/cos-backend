package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.user.PermissionValidator;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMockExamResultService {

    private final UserAnswerUpdator userAnswerUpdator;
    private final UserAnswerManager userAnswerManager;
    private final UserReader userReader;
    private final PermissionValidator permissionValidator;

    public void reviewUserAnswer(UserId userId, UserAnswerId userAnswerId) {
        User user = userReader.read(userId);
        UserAnswer userAnswer = userAnswerManager.read(userAnswerId);
        permissionValidator.validate(userAnswer, user);
        userAnswer.review();
        userAnswerUpdator.update(userAnswer);
    }
}
