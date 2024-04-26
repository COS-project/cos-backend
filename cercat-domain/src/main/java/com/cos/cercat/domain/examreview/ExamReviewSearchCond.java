package com.cos.cercat.domain.examreview;


public record ExamReviewSearchCond(
        Integer startMonths,
        Integer endPreMonths,
        ExamDifficulty examDifficulty
) {
}
