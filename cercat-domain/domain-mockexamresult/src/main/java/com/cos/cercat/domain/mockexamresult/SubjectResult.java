package com.cos.cercat.domain.mockexamresult;


import com.cos.cercat.domain.certificate.Subject;

public record SubjectResult(
        Subject subject,
        Integer score,
        Integer numberOfCorrect,
        Long totalTakenTime,
        Integer correctRate
) {
}
