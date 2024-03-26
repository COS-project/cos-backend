package com.cos.cercat.domain.examReview.dto;

import com.cos.cercat.domain.examReview.domain.ExamDifficulty;

public record ExamReviewSearchCond(
        Integer startMonths,
        Integer endPreMonths,
        ExamDifficulty examDifficulty
) {
}
