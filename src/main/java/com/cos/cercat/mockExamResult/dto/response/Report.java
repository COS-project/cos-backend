package com.cos.cercat.mockExamResult.dto.response;

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
