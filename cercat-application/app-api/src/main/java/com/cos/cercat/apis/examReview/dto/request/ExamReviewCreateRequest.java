package com.cos.cercat.apis.examReview.dto.request;

import com.cos.cercat.domain.CertificateExamEntity;
import com.cos.cercat.domain.ExamDifficulty;
import com.cos.cercat.domain.ExamReview;
import com.cos.cercat.domain.UserEntity;

public record ExamReviewCreateRequest(
        ExamDifficulty examDifficulty,
        String content
) {

    public ExamReview toEntity(UserEntity userEntity, CertificateExamEntity certificateExamEntity, int prepareMonths) {
        return new ExamReview(
                userEntity,
                certificateExamEntity,
                examDifficulty,
                prepareMonths,
                content
        );
    }

}
