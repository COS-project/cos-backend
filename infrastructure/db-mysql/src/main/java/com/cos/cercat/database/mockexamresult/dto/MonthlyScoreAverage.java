package com.cos.cercat.database.mockexamresult.dto;

import com.cos.cercat.database.common.util.ScoreFormatter;
import com.cos.cercat.domain.mockexamresult.MonthlyScoreData;
import com.cos.cercat.domain.mockexamresult.ScoreData;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.Month;

@Getter
public class MonthlyScoreAverage {
    private final int maxScore;
    private final Double scoreAverage;
    private final Month month;

    @QueryProjection
    public MonthlyScoreAverage(int maxScore, Double scoreAverage, int month) {
        this.maxScore = maxScore;
        this.scoreAverage = ScoreFormatter.formatScore(scoreAverage);
        this.month = Month.of(month);
    }


    public ScoreData toScoreData() {
        return new MonthlyScoreData(
                maxScore,
                scoreAverage,
                month
        );
    }
}
