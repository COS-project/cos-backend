package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.domain.mockExamResult.dto.ScoreAverage;

import java.util.List;

public record Report(
        double average,
        List<? extends ScoreAverage> scoreAVGList
) {

    public static Report from(List<? extends ScoreAverage> scoreAVGList) {
        return new Report(
                scoreAVGList.stream().mapToDouble(ScoreAverage::getScoreAverage).average().orElse(0),
                scoreAVGList
        );
    }

}
