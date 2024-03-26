package com.cos.cercat.dto;

import com.cos.cercat.domain.ExamDifficulty;

public record ExamReviewSearchCond(
        Integer startMonths,
        Integer endPreMonths,
        ExamDifficulty examDifficulty
) {
}
