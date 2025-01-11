package com.cos.cercat.domain.certificate;

import java.time.LocalDateTime;

public record ExamSchedule(
        LocalDateTime applicationStartDateTime,
        LocalDateTime applicationDeadlineDateTime,
        LocalDateTime resultAnnouncementDateTime,
        LocalDateTime examDateTime
) {
    public static ExamSchedule of(LocalDateTime applicationStartDateTime,
                                  LocalDateTime applicationDeadlineDateTime,
                                  LocalDateTime ResultAnnouncementDateTime,
                                  LocalDateTime examDateTime
    ) {
        return new ExamSchedule(
                applicationStartDateTime,
                applicationDeadlineDateTime,
                ResultAnnouncementDateTime,
                examDateTime
        );
    }
}
