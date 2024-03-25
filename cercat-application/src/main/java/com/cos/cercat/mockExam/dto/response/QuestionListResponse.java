package com.cos.cercat.mockExam.dto.response;

import java.util.List;

public record QuestionListResponse(
        List<QuestionResponse> questionsResponse
) {

    public static QuestionListResponse from(List<QuestionResponse> questionResponses) {
        return new QuestionListResponse(
                questionResponses
        );
    }
}
