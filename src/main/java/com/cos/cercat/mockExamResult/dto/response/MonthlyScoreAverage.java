package com.cos.cercat.mockExamResult.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

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
