package com.cos.cercat.apis.mockExamResult.app.usecase;

import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.service.UserAnswerService;
import com.cos.cercat.domain.UserAnswer;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class MockExamResultUpdateUseCase {

    private final UserAnswerService userAnswerService;
    private final UserService userService;

    @Transactional
    public void reviewUserAnswers(Long userAnswerId, Long userId) {
        UserEntity userEntity = userService.getUser(userId);
        UserAnswer userAnswer = userAnswerService.getUserAnswer(userAnswerId);

        if (!userAnswer.isAuthorized(userEntity)) {
            log.warn("userEntity - {}, userAnswerId - {} 권한이 없습니다.", userId, userAnswerId);
            throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
        }

        userAnswer.review();
        log.info("userEntity - {}, userAnswerId - {} 틀린 문제 더이상 보지 않기", userId, userAnswerId);
    }
}
