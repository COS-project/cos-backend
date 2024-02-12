package com.cos.cercat.mockExamResult.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import org.springframework.cglib.core.Local;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public record DailyScoreAVG(
        double scoreAVG,
        DayOfWeek dayOfWeek,
        Date date
) {

    @QueryProjection
    public DailyScoreAVG(Double scoreAVG, Integer dayOfWeek, Date date) {
        this(
                scoreAVG,
                DayOfWeek.of((dayOfWeek > 1) ? dayOfWeek - 1 : 7),
                date
        );
    }
}
