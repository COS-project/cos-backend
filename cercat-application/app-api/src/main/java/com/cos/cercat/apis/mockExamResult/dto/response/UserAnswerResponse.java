package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.mockExam.response.QuestionResponse;
import com.cos.cercat.mockexamresult.UserAnswer;

public record UserAnswerResponse(
        QuestionResponse question,
        Long userAnswerId,
        int selectOptionSeq,
        long takenTime,
        boolean isCorrect
) {

    public static UserAnswerResponse from(UserAnswer userAnswer) {
        return new UserAnswerResponse(
                QuestionResponse.from(userAnswer.getQuestion()),
                userAnswer.getId(),
                userAnswer.getSelectOptionSeq(),
                userAnswer.getTakenTime(),
                userAnswer.isCorrect());
    }
}
