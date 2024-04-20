package com.cos.cercat.dto;

import com.cos.cercat.domain.SubjectEntity;

public record SubjectResultsAVG(
        SubjectEntity subjectEntity,
        Double correctRate,
        Double totalTakenTime
) {
}
