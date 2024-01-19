package com.cos.cercat.certificate.dto.response;

import com.cos.cercat.certificate.domain.Certificate;

public record CertificateResponse(
        Long certificateId,
        String certificateName,
        Long timeLimit
) {
    public static CertificateResponse from(Certificate entity) {
        return new CertificateResponse(
                entity.getId(),
                entity.getCertificateName(),
                entity.getTimeLimit()
        );
    }
}
