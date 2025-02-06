package com.cos.cercat.database.mockexamresult.dto;

import com.cos.cercat.database.certificate.entity.SubjectEntity;
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
