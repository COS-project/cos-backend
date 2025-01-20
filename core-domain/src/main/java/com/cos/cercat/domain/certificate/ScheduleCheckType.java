package com.cos.cercat.domain.certificate;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScheduleCheckType {
    BEFORE_APPLICATION(
            ExamScheduleType.APPLICATION_START,
            1
    ),
    START_APPLICATION(
            ExamScheduleType.APPLICATION_START,
            0
    ),
    BEFORE_DEADLINE(
            ExamScheduleType.DEADLINE,
            1
    );

    private final ExamScheduleType examScheduleType;
    private final int daysToAdvance;   // 오늘로부터 몇 일 앞서서 알림을 줄 것인지

    public LocalDateTime getDate() {
        return LocalDateTime.now().plusDays(daysToAdvance);
    }

}
