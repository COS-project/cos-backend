package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.domain.UserAnswer;

public record UserAnswerResponse(
        QuestionResponse question,
        Long userAnswerId,
        int selectOptionSeq,
        long takenTime,
        boolean isCorrect
) {
    public static UserAnswerResponse from(UserAnswer entity) {
        return new UserAnswerResponse(
                QuestionResponse.from(entity.getQuestionEntity()),
                entity.getId(),
                entity.getSelectOptionSeq(),
                entity.getTakenTime(),
                entity.isCorrect()
        );
    }
}
