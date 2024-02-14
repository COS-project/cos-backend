package com.cos.cercat.mockExam.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    public static LocalDateTime getThisSunday() {
        return LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().plusDays(1).atStartOfDay();
    }

    public static LocalDateTime getThisMonday() {
        return LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
    }

    public static LocalDateTime getLastDayOfMonth() {
        return LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().plusDays(1).atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfMonth() {
        return LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfYear() {
        return LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear()).toLocalDate().atStartOfDay();
    }

    public static LocalDateTime getLastDayOfYear() {
        return LocalDateTime.now().with(TemporalAdjusters.lastDayOfYear()).toLocalDate().plusDays(1).atStartOfDay();
    }
}
