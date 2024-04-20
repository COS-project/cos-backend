package com.cos.cercat.dto;

import com.cos.cercat.domain.CertificateEntity;

public record CertificateDTO(
        Long certificateId,
        String certificateName
) {

    public static CertificateDTO from(CertificateEntity entity) {
        return new CertificateDTO(
                entity.getId(),
                entity.getCertificateName()
        );
    }

    public CertificateEntity toEntity() {
        return new CertificateEntity(
                certificateId,
                certificateName
        );
    }


}
