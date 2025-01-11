package com.cos.cercat.domain.mockexamresult;

import lombok.Getter;

@Getter
public class WeeklyScoreData extends ScoreData {
    private final int weekOfMonth;

    public WeeklyScoreData(double scoreAverage, int weekOfMonth) {
        super(scoreAverage);
        this.weekOfMonth = weekOfMonth;
    }
}
