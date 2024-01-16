package com.cos.cercat.mockExam.dto.request;

import com.cos.cercat.user.domain.User;
import com.cos.cercat.question.domain.Question;
import com.cos.cercat.mockExam.domain.UserAnswer;

public record UserAnswerRequest(
        Long questionId,
        String selectOption,
        long takenTime,
        boolean is_correct
) {
    public UserAnswer toEntity(Question question, User user) {
        return UserAnswer.of(
                user,
                question,
                selectOption,
                takenTime,
                is_correct
        );
    }
}
