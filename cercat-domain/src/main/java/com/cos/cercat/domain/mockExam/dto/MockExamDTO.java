package com.cos.cercat.domain.mockExam.dto;

import com.cos.cercat.domain.certificate.dto.CertificateDTO;

public record MockExamDTO(
        Long mockExamId,
        Integer examYear,
        Integer round,
        CertificateDTO certificateDTO
) {

    public static MockExamDTO of(Integer examYear, Integer round, CertificateDTO certificateDTO) {
        return new MockExamDTO(
                null,
                examYear,
                round,
                certificateDTO
        );
    }

}
