package com.cos.cercat.mockExamResult.dto.response;

import lombok.Getter;

@Getter
public class WeeklyScoreAverage extends ScoreAverage {

    private final int month;
    private final int weekOfMonth;

    private WeeklyScoreAverage(double scoreAverage, int month, int weekOfMonth) {
        super(scoreAverage);
        this.month = month;
        this.weekOfMonth = weekOfMonth;
    }

    public static WeeklyScoreAverage of(double scoreAverage, int month, int weekOfMonth) {
        return new WeeklyScoreAverage(
                scoreAverage,
                month,
                weekOfMonth
        );
    }
}
