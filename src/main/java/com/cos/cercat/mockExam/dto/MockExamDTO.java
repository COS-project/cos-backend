package com.cos.cercat.mockExam.dto;

import com.cos.cercat.certificate.dto.CertificateDTO;
import com.cos.cercat.mockExam.domain.MockExam;

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
