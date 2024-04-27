package com.cos.cercat.mockexamresult;

public record NewUserAnswer(
        Long questionId,
        Integer selectOptionSeq,
        Long takenTime,
        boolean isCorrect
) {
}
