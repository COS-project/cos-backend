package com.cos.cercat.dto;

import com.cos.cercat.domain.Subject;

public record SubjectResultsAVG(
        Subject subject,
        Double correctRate,
        Double totalTakenTime
) {
}
