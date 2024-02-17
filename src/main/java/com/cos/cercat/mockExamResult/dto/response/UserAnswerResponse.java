package com.cos.cercat.mockExamResult.dto.response;

import com.cos.cercat.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.mockExamResult.domain.UserAnswer;

public record UserAnswerResponse(
        QuestionResponse question,
        int selectOption,
        long takenTime,
        boolean isCorrect
) {
    public static UserAnswerResponse from(UserAnswer entity) {
        return new UserAnswerResponse(
                QuestionResponse.from(entity.getQuestion()),
                entity.getSelectOptionSeq(),
                entity.getTakenTime(),
                entity.isCorrect()
        );
    }
}
