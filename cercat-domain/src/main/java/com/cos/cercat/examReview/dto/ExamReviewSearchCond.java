package com.cos.cercat.examReview.dto;

import com.cos.cercat.examReview.domain.ExamDifficulty;

public record ExamReviewSearchCond(
        Integer startMonths,
        Integer endPreMonths,
        ExamDifficulty examDifficulty
) {
}
