package com.cos.cercat.certificate.dto.response;

import com.cos.cercat.certificate.domain.CertificateExam;

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
