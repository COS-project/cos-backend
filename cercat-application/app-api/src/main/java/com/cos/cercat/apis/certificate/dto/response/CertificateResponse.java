package com.cos.cercat.apis.certificate.dto.response;

import com.cos.cercat.domain.certificate.Certificate;

public record CertificateResponse(
        Long certificateId,
        String certificateName
) {
    public static CertificateResponse from(Certificate certificate) {
        return new CertificateResponse(
                certificate.id(),
                certificate.certificateName()
        );
    }
}
