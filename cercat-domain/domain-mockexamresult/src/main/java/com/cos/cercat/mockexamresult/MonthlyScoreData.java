package com.cos.cercat.mockexamresult;

import lombok.Getter;

import java.time.Month;

@Getter
public class MonthlyScoreData extends ScoreData {
    private final Month month;

    public MonthlyScoreData(double scoreAverage, Month month) {
        super(scoreAverage);
        this.month = month;
    }

    public int getMonth() {
        return month.getValue();
    }
}
