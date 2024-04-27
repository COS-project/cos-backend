package com.cos.cercat.mockexamresult;

import java.util.List;

public record NewMockExamResult(
        int totalScore,
        int round,
        List<NewSubjectResult> newSubjectResults
) {

    public static NewMockExamResult of(int round, List<NewSubjectResult> newSubjectResults) {

        int totalScore = newSubjectResults.stream()
                .map(NewSubjectResult::score)
                .reduce(0, Integer::sum);

        return new NewMockExamResult(
                totalScore,
                round,
                newSubjectResults
        );
    }
}
