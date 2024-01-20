package com.cos.cercat.mockExamResult.dto.response;

import com.cos.cercat.certificate.dto.response.CertificateResponse;
import com.cos.cercat.mockExam.domain.MockExam;

public record MockExamWithInfoResponse(
        Long mockExamId,
        Integer examYear,
        Integer round,
        Long timeLimit,
        Integer numberOfQuestions,
        CertificateResponse certificate
) {
    public static MockExamWithInfoResponse from(MockExam entity) {
        return new MockExamWithInfoResponse(
                entity.getId(),
                entity.getExamYear(),
                entity.getRound(),
                entity.getCertificate().getExamInfo().getExamTimeLimit().getWrittenExamTimeLimit(),
                entity.getCertificate().getExamInfo().getNumberOfQuestions(),
                CertificateResponse.from(entity.getCertificate())
        );
    }
}
