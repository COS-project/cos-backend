package com.cos.cercat.apis.mockExamResult.dto.request;

import com.cos.cercat.domain.User;
import com.cos.cercat.domain.Question;
import com.cos.cercat.domain.UserAnswer;

import java.util.Objects;

public record UserAnswerRequest(
        Long questionId,
        Integer selectOptionSeq,
        Long takenTime,
        boolean isCorrect
) {
    public UserAnswer toEntity(Question question, User user) {

        Objects.requireNonNull(selectOptionSeq, "Selected option must not be null");
        Objects.requireNonNull(takenTime, "Taken time must not be null");

        return UserAnswer.builder()
                .user(user)
                .question(question)
                .selectOptionSeq(selectOptionSeq)
                .takenTime(takenTime)
                .isCorrect(isCorrect)
                .isReviewed(false)
                .build();
    }
}
