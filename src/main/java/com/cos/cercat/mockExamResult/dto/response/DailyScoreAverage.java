package com.cos.cercat.mockExamResult.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Date;

@Getter
public class DailyScoreAverage extends ScoreAverage {

    private final DayOfWeek dayOfWeek;

    private final Date date;

    private DailyScoreAverage(double scoreAverage, DayOfWeek dayOfWeek, Date date) {
        super(scoreAverage);
        this.dayOfWeek = dayOfWeek;
        this.date = date;
    }

    @QueryProjection
    public DailyScoreAverage(Double scoreAverage, Integer dayOfWeek, Date date) {
        super(scoreAverage);
        this.dayOfWeek = DayOfWeek.of((dayOfWeek > 1) ? dayOfWeek - 1 : 7);
        this.date = date;

    }

    public static DailyScoreAverage of(double scoreAverage, DayOfWeek dayOfWeek, Date date) {
        return new DailyScoreAverage(
                scoreAverage,
                dayOfWeek,
                date
        );
    }
}
