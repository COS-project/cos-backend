package com.cos.cercat.database.mockexamresult.dto;

import com.cos.cercat.database.common.util.ScoreFormatter;
import com.cos.cercat.domain.mockexamresult.DailyScoreData;
import com.cos.cercat.domain.mockexamresult.ScoreData;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Date;

@Getter
public class DailyScoreAverage {
    private final Double scoreAverage;
    private final DayOfWeek dayOfWeek;
    private final Date date;

    @QueryProjection
    public DailyScoreAverage(Double scoreAverage,
                             Integer dayOfWeek,
                             Date date) {
        this.scoreAverage = ScoreFormatter.formatScore(scoreAverage);
        this.dayOfWeek = DayOfWeek.of(
                (dayOfWeek > 1) ?
                        dayOfWeek - 1
                        : 7);
        this.date = date;
    }

    public ScoreData toScoreData() {
        return new DailyScoreData(
                scoreAverage,
                dayOfWeek,
                date
        );
    }
}
