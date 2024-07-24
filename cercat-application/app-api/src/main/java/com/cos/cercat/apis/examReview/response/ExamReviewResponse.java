package com.cos.cercat.apis.examReview.response;

import com.cos.cercat.apis.user.response.UserResponse;
import com.cos.cercat.domain.examreview.ExamDifficulty;
import com.cos.cercat.domain.examreview.ExamReview;

import java.time.LocalDateTime;

public record ExamReviewResponse(
        ExamDifficulty examDifficulty,
        Integer prepareMonths,
        String content,
        UserResponse user,
        LocalDateTime createdAt
) {

    public static ExamReviewResponse from(ExamReview examReview) {
        return new ExamReviewResponse(
                examReview.content().examDifficulty(),
                examReview.prepareMonths(),
                examReview.content().content(),
                UserResponse.from(examReview.user()),
                examReview.createdAt()
        );
    }

}
