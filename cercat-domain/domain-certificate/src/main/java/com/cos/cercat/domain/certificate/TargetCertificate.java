package com.cos.cercat.domain.certificate;

public record TargetCertificate(
        Long certificateId
) {
    public static TargetCertificate from(Long certificateId) {
        return new TargetCertificate(certificateId);
    }
}
