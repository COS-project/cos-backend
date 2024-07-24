package com.cos.cercat.domain.learning;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
@AllArgsConstructor
public class StudySchedule {
    public static final Integer SUNDAY_VALUE = 7;

    private Long id;
    private ScheduleType scheduleType;
    private DayOfWeek repeatDayOfWeek;

    public StudySchedule(ScheduleType scheduleType, DayOfWeek repeatDayOfWeek) {
        this.scheduleType = scheduleType;
        this.repeatDayOfWeek = repeatDayOfWeek;
    }

    public static StudySchedule from(ScheduleType scheduleType, Integer dayOfWeakValue) {
        return new StudySchedule(
                scheduleType,
                DayOfWeek.of((dayOfWeakValue > 0) ? dayOfWeakValue : SUNDAY_VALUE)
        );
    }
}
