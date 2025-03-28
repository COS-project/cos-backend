package com.cos.cercat.domain.mockexamresult;


import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Date;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyScoreData extends ScoreData {
    private DayOfWeek dayOfWeek;
    private Date date;

    public DailyScoreData(Double scoreAverage, DayOfWeek dayOfWeek, Date date) {
        super(scoreAverage);
        this.dayOfWeek = dayOfWeek;
        this.date = date;
    }
}
