package com.cos.cercat.apis.examReview.dto.request;

import com.cos.cercat.domain.CertificateExam;
import com.cos.cercat.domain.ExamDifficulty;
import com.cos.cercat.domain.ExamReview;
import com.cos.cercat.domain.User;

public record ExamReviewCreateRequest(
        ExamDifficulty examDifficulty,
        String content
) {

    public ExamReview toEntity(User user, CertificateExam certificateExam, int prepareMonths) {
        return new ExamReview(
                user,
                certificateExam,
                examDifficulty,
                prepareMonths,
                content
        );
    }

}
