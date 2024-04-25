package com.cos.cercat.domain.learning;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
@AllArgsConstructor
public class StudySchedule {
    public static final Integer SUNDAY_VALUE = 7;

    private Long id;
    private RepeatType repeatType;
    private DayOfWeek repeatDayOfWeek;

    public StudySchedule(RepeatType repeatType, DayOfWeek repeatDayOfWeek) {
        this.repeatType = repeatType;
        this.repeatDayOfWeek = repeatDayOfWeek;
    }

    public static StudySchedule from(RepeatType repeatType, Integer dayOfWeakValue) {
        return new StudySchedule(
                repeatType,
                DayOfWeek.of((dayOfWeakValue > 0) ? dayOfWeakValue : SUNDAY_VALUE)
        );
    }
}
