package com.cos.cercat.apis.examReview.dto.response;

import com.cos.cercat.domain.ExamDifficulty;
import com.cos.cercat.domain.ExamReview;
import com.cos.cercat.apis.user.dto.response.UserResponse;

import java.time.LocalDateTime;

public record ExamReviewResponse(
        ExamDifficulty examDifficulty,
        Integer prepareMonths,
        String content,
        UserResponse user,
        LocalDateTime createdAt
) {

    public static ExamReviewResponse from(ExamReview entity) {
        return new ExamReviewResponse(
                entity.getExamDifficulty(),
                entity.getPrepareMonths(),
                entity.getContent(),
                UserResponse.fromEntity(entity.getUserEntity()),
                entity.getCreatedAt()
        );
    }

}
