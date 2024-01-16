package com.cos.cercat.mockExamResult.dto.response;

import com.cos.cercat.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.mockExamResult.domain.UserAnswer;

public record UserAnswerResponse(
        QuestionResponse question,
        String selectOption,
        long takenTime,
        boolean is_correct
) {
    public static UserAnswerResponse from(UserAnswer entity) {
        return new UserAnswerResponse(
                QuestionResponse.from(entity.getQuestion()),
                entity.getSelectOption(),
                entity.getTakenTime(),
                entity.getIs_correct()
        );
    }

}
