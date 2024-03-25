package com.cos.cercat.mockExamResult.dto;

import com.cos.cercat.certificate.domain.Subject;

public record SubjectResultsAVG(
        Subject subject,
        Double correctRate,
        Double totalTakenTime
) {
}
