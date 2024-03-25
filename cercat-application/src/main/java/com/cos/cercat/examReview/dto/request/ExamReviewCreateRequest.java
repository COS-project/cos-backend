package com.cos.cercat.examReview.dto.request;

import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.examReview.domain.ExamDifficulty;
import com.cos.cercat.examReview.domain.ExamReview;
import com.cos.cercat.user.domain.User;

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
