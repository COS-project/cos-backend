package com.cos.cercat.domain.learning;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
@AllArgsConstructor
public class GoalSchedule {
    public static final Integer SUNDAY_VALUE = 7;

    private Long id;
    private ScheduleType scheduleType;
    private DayOfWeek repeatDayOfWeek;

    public GoalSchedule(ScheduleType scheduleType, DayOfWeek repeatDayOfWeek) {
        this.scheduleType = scheduleType;
        this.repeatDayOfWeek = repeatDayOfWeek;
    }

    public static GoalSchedule from(ScheduleType scheduleType, Integer dayOfWeakValue) {
        return new GoalSchedule(
                scheduleType,
                DayOfWeek.of((dayOfWeakValue > 0) ? dayOfWeakValue : SUNDAY_VALUE)
        );
    }
}
