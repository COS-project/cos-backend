package com.cos.cercat.examreview;


import com.cos.cercat.user.User;

import java.time.LocalDateTime;

public record ExamReview(
        Long id,
        User user,
        ExamReviewContent content,
        int prepareMonths,
        LocalDateTime createdAt
) {
}
