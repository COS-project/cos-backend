package com.cos.cercat.mockExam.api.request;

public record UserAnswerRequest(
        Long questionId,
        String selectOption,
        long takenTime,
        boolean is_correct
) {

}
