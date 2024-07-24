package com.cos.cercat.domain.mockexamresult;


import com.cos.cercat.domain.certificate.Subject;

public record SubjectResultStatistics(
        Subject subject,
        int correctRate,
        long totalTakenTime
) {
}
