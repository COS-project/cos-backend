package com.cos.cercat.apis.certificate.dto.response;

import com.cos.cercat.domain.CertificateExam;

public record CertificateExamResponse(
        Integer examYear,
        Integer round,
        ExamInfoResponse examInfo
) {
    public static CertificateExamResponse from(CertificateExam entity) {
        return new CertificateExamResponse(
                entity.getExamYear(),
                entity.getRound(),
                ExamInfoResponse.from(entity.getExamInfo())
        );
    }
}
