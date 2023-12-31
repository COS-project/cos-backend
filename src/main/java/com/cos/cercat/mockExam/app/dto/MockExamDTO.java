package com.cos.cercat.mockExam.app.dto;

import com.cos.cercat.mockExam.domain.entity.MockExam;

public record MockExamDTO(
        Long mockExamId,
        String name,
        Integer examYear,
        Integer round,
        CertificateDTO certificateDTO
) {

    public static MockExamDTO from(MockExam entity) {
        return new MockExamDTO(
                entity.getId(),
                entity.getName(),
                entity.getExamYear(),
                entity.getRound(),
                CertificateDTO.from(entity.getCertificate())
        );
    }

    public static MockExamDTO of(String name, Integer examYear, Integer round, CertificateDTO certificateDTO) {
        return new MockExamDTO(
                null,
                name,
                examYear,
                round,
                certificateDTO
        );
    }

}
