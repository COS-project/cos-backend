package com.cos.cercat.infra.dto;

import com.cos.cercat.common.util.ScoreFormatter;
import com.cos.cercat.domain.mockexamresult.MonthlyScoreData;
import com.cos.cercat.domain.mockexamresult.ScoreData;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.Month;

@Getter
public class MonthlyScoreAverage {
    private final Double scoreAverage;
    private final Month month;

    @QueryProjection
    public MonthlyScoreAverage(Double scoreAverage, int month) {
        this.scoreAverage = ScoreFormatter.formatScore(scoreAverage);
        this.month = Month.of(month);
    }


    public ScoreData toScoreData() {
        return new MonthlyScoreData(
                scoreAverage,
                month
        );
    }
}
