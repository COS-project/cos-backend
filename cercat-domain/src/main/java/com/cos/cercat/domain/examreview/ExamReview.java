package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.user.User;

import java.time.LocalDateTime;

public record ExamReview(
        Long id,
        User user,
        ExamReviewContent content,
        int prepareMonths,
        LocalDateTime createdAt
) {
}
