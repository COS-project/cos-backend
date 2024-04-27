package com.cos.cercat.examreview;


public record ExamReviewSearchCond(
        Integer startMonths,
        Integer endPreMonths,
        ExamDifficulty examDifficulty
) {
}
