package com.cos.cercat.mockExamResult.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.Month;

public class MonthlyScoreAverage extends ScoreAverage {

    private final Month month;

    @QueryProjection
    public MonthlyScoreAverage(double scoreAverage, int month) {
        super(scoreAverage);
        this.month = Month.of(month);
    }

    public int getMonth() {
        return month.getValue();
    }
}
