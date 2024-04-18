package com.cos.cercat.apis.certificate.dto.response;

import com.cos.cercat.domain.CertificateEntity;

public record CertificateResponse(
        Long certificateId,
        String certificateName
) {
    public static CertificateResponse from(CertificateEntity entity) {
        return new CertificateResponse(
                entity.getId(),
                entity.getCertificateName()
        );
    }
}
