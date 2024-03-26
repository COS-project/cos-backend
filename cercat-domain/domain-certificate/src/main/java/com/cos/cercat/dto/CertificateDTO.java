package com.cos.cercat.dto;

import com.cos.cercat.domain.Certificate;

public record CertificateDTO(
        Long certificateId,
        String certificateName
) {

    public static CertificateDTO from(Certificate entity) {
        return new CertificateDTO(
                entity.getId(),
                entity.getCertificateName()
        );
    }

    public Certificate toEntity() {
        return new Certificate(
                certificateId,
                certificateName
        );
    }


}
