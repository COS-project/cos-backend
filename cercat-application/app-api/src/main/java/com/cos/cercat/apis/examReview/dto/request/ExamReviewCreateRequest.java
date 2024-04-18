package com.cos.cercat.apis.examReview.dto.request;

import com.cos.cercat.domain.CertificateExam;
import com.cos.cercat.domain.ExamDifficulty;
import com.cos.cercat.domain.ExamReview;
import com.cos.cercat.domain.UserEntity;

public record ExamReviewCreateRequest(
        ExamDifficulty examDifficulty,
        String content
) {

    public ExamReview toEntity(UserEntity userEntity, CertificateExam certificateExam, int prepareMonths) {
        return new ExamReview(
                userEntity,
                certificateExam,
                examDifficulty,
                prepareMonths,
                content
        );
    }

}
