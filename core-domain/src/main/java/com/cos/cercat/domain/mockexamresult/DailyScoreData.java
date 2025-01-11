package com.cos.cercat.domain.mockexamresult;


import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Date;

@Getter
public class DailyScoreData extends ScoreData {
    private final DayOfWeek dayOfWeek;
    private final Date date;

    public DailyScoreData(Double scoreAverage, DayOfWeek dayOfWeek, Date date) {
        super(scoreAverage);
        this.dayOfWeek = dayOfWeek;
        this.date = date;
    }
}
