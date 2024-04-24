package com.cos.cercat.apis.mockExam.response;

import java.util.List;
import java.util.Map;

public record ExamYearWithRoundsResponse(
        Map<Integer, List<Integer>> examYearWithRounds
) {
    public static ExamYearWithRoundsResponse from(Map<Integer, List<Integer>> examYearWithRounds) {
        return new ExamYearWithRoundsResponse(
                examYearWithRounds
        );
    }
}
