package com.cos.cercat.mockExam.api.request;

import com.cos.cercat.Member.domain.entity.User;
import com.cos.cercat.mockExam.domain.entity.Question;
import com.cos.cercat.mockExam.domain.entity.UserAnswer;

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
