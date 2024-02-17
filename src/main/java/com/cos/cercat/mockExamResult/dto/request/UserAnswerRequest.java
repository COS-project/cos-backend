package com.cos.cercat.mockExamResult.dto.request;

import com.cos.cercat.user.domain.User;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.mockExamResult.domain.UserAnswer;

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

        return UserAnswer.of(
                user,
                question,
                selectOptionSeq,
                takenTime,
                isCorrect
        );
    }
}
