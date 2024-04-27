package com.cos.cercat.mockexamresult;


import com.cos.cercat.certificate.Subject;

public record SubjectResult(
        Subject subject,
        Integer score,
        Integer numberOfCorrect,
        Long totalTakenTime,
        Integer correctRate) {
}
