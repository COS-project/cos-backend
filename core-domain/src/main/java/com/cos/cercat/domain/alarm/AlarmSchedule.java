package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.certificate.ExamScheduleType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmSchedule {
    BEFORE_APPLICATION(
            ExamScheduleType.APPLICATION_START,
            1,
            AlarmType.BEFORE_APPLICATION
    ),
    START_APPLICATION(
            ExamScheduleType.APPLICATION_START,
            0,
            AlarmType.START_APPLICATION
    ),
    BEFORE_DEADLINE(
            ExamScheduleType.DEADLINE,
            1,
            AlarmType.BEFORE_DEADLINE
    );

    private final ExamScheduleType examScheduleType;
    private final int daysToAdvance;   // 오늘로부터 몇 일 앞서서 알림을 줄 것인지
    private final AlarmType alarmType;

    public LocalDateTime getDate() {
        return LocalDateTime.now().plusDays(daysToAdvance);
    }

}
