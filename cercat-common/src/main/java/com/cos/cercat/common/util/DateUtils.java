package com.cos.cercat.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    public static LocalDateTime getThisSaturday(LocalDate dateTime) {
        return dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).plusDays(1).atStartOfDay();
    }

    public static LocalDateTime getThisSunday(LocalDate dateTime) {
        return dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay();
    }

    public static LocalDateTime getLastDayOfMonth(LocalDate dateTime) {
        return dateTime.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfMonth(LocalDate dateTime) {
        return dateTime.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfYear(LocalDate dateTime) {
        return dateTime.with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
    }

    public static LocalDateTime getLastDayOfYear(LocalDate dateTime) {
        return dateTime.with(TemporalAdjusters.lastDayOfYear()).plusDays(1).atStartOfDay();
    }
}
