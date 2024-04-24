package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.Subject;
import lombok.Getter;

@Getter
public class SubjectResult {
    private final Subject subject;
    private final Integer score;
    private final Integer numberOfCorrect;
    private final Long totalTakenTime;
    private final Integer correctRate;

    public SubjectResult(Subject subject, Integer score, Integer numberOfCorrect, Long totalTakenTime, Integer correctRate) {
        this.subject = subject;
        this.score = score;
        this.numberOfCorrect = numberOfCorrect;
        this.totalTakenTime = totalTakenTime;
        this.correctRate = correctRate;
    }
}
