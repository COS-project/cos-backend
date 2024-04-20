package com.cos.cercat.apis.mockExamResult.dto.request;

import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.UserAnswer;

import java.util.Objects;

public record UserAnswerRequest(
        Long questionId,
        Integer selectOptionSeq,
        Long takenTime,
        boolean isCorrect
) {
    public UserAnswer toEntity(QuestionEntity questionEntity, UserEntity userEntity) {

        Objects.requireNonNull(selectOptionSeq, "Selected option must not be null");
        Objects.requireNonNull(takenTime, "Taken time must not be null");

        return UserAnswer.builder()
                .userEntity(userEntity)
                .questionEntity(questionEntity)
                .selectOptionSeq(selectOptionSeq)
                .takenTime(takenTime)
                .isCorrect(isCorrect)
                .isReviewed(false)
                .build();
    }
}
