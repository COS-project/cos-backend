package com.cos.cercat.database.mockexamresult.dto;

import com.cos.cercat.database.common.util.ScoreFormatter;
import com.cos.cercat.domain.mockexamresult.ScoreData;
import com.cos.cercat.domain.mockexamresult.WeeklyScoreData;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class WeeklyScoreAverage {
    private final int maxScore;
    private final Double scoreAverage;
    private final int weekOfMonth;

    @QueryProjection
    public WeeklyScoreAverage(int maxScore, Double scoreAverage, Integer weekOfMonth) {
        this.maxScore = maxScore;
        this.scoreAverage = ScoreFormatter.formatScore(scoreAverage);
        this.weekOfMonth = weekOfMonth;
    }

    public ScoreData toScoreData() {
        return new WeeklyScoreData(
                maxScore,
                scoreAverage,
                weekOfMonth
        );
    }

}
