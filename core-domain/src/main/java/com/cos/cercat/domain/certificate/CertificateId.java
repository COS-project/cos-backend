package com.cos.cercat.domain.certificate;

public record CertificateId(
        Long value
) {
    public static CertificateId from(Long certificateId) {
        return new CertificateId(certificateId);
    }

    public static CertificateId from(String certificateId) {
        return new CertificateId(Long.parseLong(certificateId));
    }
}
