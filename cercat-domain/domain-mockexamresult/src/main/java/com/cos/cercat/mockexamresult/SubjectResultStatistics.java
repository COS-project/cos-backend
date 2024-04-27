package com.cos.cercat.mockexamresult;


import com.cos.cercat.certificate.Subject;

public record SubjectResultStatistics(
        Subject subject,
        int correctRate,
        long totalTakenTime
) {
}
