package com.cos.cercat.dto;

import com.cos.cercat.dto.CertificateDTO;

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
