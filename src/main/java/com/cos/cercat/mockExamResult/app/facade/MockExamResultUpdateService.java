package com.cos.cercat.mockExamResult.app.facade;

import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.mockExamResult.app.UserAnswerService;
import com.cos.cercat.mockExamResult.domain.UserAnswer;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MockExamResultUpdateService {

    private final UserAnswerService userAnswerService;
    private final UserService userService;

    @Transactional
    public void reviewUserAnswers(Long userAnswerId, Long userId) {
        User user = userService.getUser(userId);
        UserAnswer userAnswer = userAnswerService.getUserAnswer(userAnswerId);

        if (!userAnswer.isAuthorized(user)) {
            log.warn("user - {}, userAnswerId - {} 권한이 없습니다.", userId, userAnswerId);
            throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
        }

        userAnswer.review();
        log.info("user - {}, userAnswerId - {} 틀린 문제 더이상 보지 않기", userId, userAnswerId);
    }
}
