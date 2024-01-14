package com.cos.cercat.mockExam.dto.response;


import com.cos.cercat.certificate.dto.response.CertificateResponse;
import com.cos.cercat.mockExam.domain.MockExam;

public record MockExamResponse(
        Long MockExamId,
        String name,
        Integer examYear,
        Integer round,
        CertificateResponse certificate
) {
    public static MockExamResponse from(MockExam entity) {
        return new MockExamResponse(
                entity.getId(),
                entity.getName(),
                entity.getExamYear(),
                entity.getRound(),
                CertificateResponse.from(entity.getCertificate())
        );
    }
}
