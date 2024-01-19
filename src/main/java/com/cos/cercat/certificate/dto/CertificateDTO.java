package com.cos.cercat.certificate.dto;

import com.cos.cercat.certificate.domain.Certificate;

public record CertificateDTO(
        Long certificateId,
        String certificateName,
        Long timeLimit
) {

    public static CertificateDTO from(Certificate entity) {
        return new CertificateDTO(
                entity.getId(),
                entity.getCertificateName(),
                entity.getTimeLimit()
        );
    }

    public Certificate toEntity() {
        return new Certificate(
                certificateId,
                certificateName,
                timeLimit
        );
    }


}
