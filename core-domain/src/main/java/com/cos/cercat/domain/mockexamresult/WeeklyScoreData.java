package com.cos.cercat.domain.mockexamresult;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeeklyScoreData extends ScoreData {
    private int weekOfMonth;

    public WeeklyScoreData(int maxScore, double scoreAverage, int weekOfMonth) {
        super(maxScore, scoreAverage);
        this.weekOfMonth = weekOfMonth;
    }
}
