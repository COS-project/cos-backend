package com.cos.cercat.mockexamresult;

import java.util.List;

public record NewSubjectResult(
        Long subjectId,
        int score,
        int numberOfCorrect,
        Long totalTakenTime,
        List<NewUserAnswer> newUserAnswers
) {
}
