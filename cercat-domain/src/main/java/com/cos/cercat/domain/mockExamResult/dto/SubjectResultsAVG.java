package com.cos.cercat.domain.mockExamResult.dto;

import com.cos.cercat.domain.certificate.domain.Subject;

public record SubjectResultsAVG(
        Subject subject,
        Double correctRate,
        Double totalTakenTime
) {
}
