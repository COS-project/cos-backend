package com.cos.cercat.mockExamResult.dto;

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

}
