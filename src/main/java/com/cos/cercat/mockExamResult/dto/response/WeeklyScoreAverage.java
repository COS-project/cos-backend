package com.cos.cercat.mockExamResult.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class WeeklyScoreAverage extends ScoreAverage {
    private final int weekOfMonth;

    @QueryProjection
    public WeeklyScoreAverage(Double scoreAverage, Integer weekOfMonth) {
        super(scoreAverage);
        this.weekOfMonth = weekOfMonth;
    }

    public static WeeklyScoreAverage of(double scoreAverage, int weekOfMonth) {
        return new WeeklyScoreAverage(
                scoreAverage,
                weekOfMonth
        );
    }
}
