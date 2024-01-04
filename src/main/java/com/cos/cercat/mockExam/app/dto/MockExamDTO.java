package com.cos.cercat.mockExam.app.dto;

import com.cos.cercat.mockExam.domain.entity.MockExam;

public record MockExamDTO(
        String name,
        Integer examYear,
        Integer round,
        CertificateDTO certificateDTO
) {

    public static MockExamDTO from(MockExam entity) {
        return new MockExamDTO(
                entity.getName(),
                entity.getExamYear(),
                entity.getRound(),
                CertificateDTO.from(entity.getCertificate())
        );
    }

}
