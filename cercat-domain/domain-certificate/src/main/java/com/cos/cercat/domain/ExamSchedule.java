package com.cos.cercat.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExamSchedule {

    private LocalDateTime applicationStartDateTime;

    private LocalDateTime applicationDeadlineDateTime;

    private LocalDateTime ResultAnnouncementDateTime;

    private LocalDateTime examDateTime;

}
