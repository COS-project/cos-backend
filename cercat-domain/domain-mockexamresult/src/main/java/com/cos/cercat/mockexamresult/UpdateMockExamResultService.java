package com.cos.cercat.mockexamresult;

import com.cos.cercat.user.PermissionValidator;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMockExamResultService {

    private final UserAnswerUpdator userAnswerUpdator;
    private final UserAnswerManager userAnswerManager;
    private final UserReader userReader;
    private final PermissionValidator permissionValidator;

    public void reviewUserAnswer(TargetUser targetUser, TargetUserAnswer targetUserAnswer) {
        User user = userReader.read(targetUser);
        UserAnswer userAnswer = userAnswerManager.read(targetUserAnswer);
        permissionValidator.validate(userAnswer, user);
        userAnswer.review();
        userAnswerUpdator.update(userAnswer);
    }
}
