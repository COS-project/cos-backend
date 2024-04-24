package com.cos.cercat.domain.mockexamresult;

public record NewUserAnswer(
        Long questionId,
        Integer selectOptionSeq,
        Long takenTime,
        boolean isCorrect
) {
}
