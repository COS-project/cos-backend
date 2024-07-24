package com.cos.cercat.dto;

import com.cos.cercat.domain.SubjectEntity;
import com.cos.cercat.domain.mockexamresult.SubjectResultStatistics;

public record SubjectResultsAVG(
        SubjectEntity subject,
        Double correctRate,
        Double totalTakenTime
) {
    public SubjectResultStatistics toDomain() {
        return new SubjectResultStatistics(
                subject.toDomain(),
                correctRate.intValue(),
                totalTakenTime.longValue()
        );
    }

}
