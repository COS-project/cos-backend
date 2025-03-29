package com.cos.cercat.domain.mockexamresult;

import lombok.Getter;

import java.time.Month;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthlyScoreData extends ScoreData {
    private Month month;

    public MonthlyScoreData(int maxScore, double scoreAverage, Month month) {
        super(maxScore, scoreAverage);
        this.month = month;
    }

    public void setMonth(int month) {
        this.month = Month.of(month);
    }

    public int getMonth() {
        return month.getValue();
    }
}
