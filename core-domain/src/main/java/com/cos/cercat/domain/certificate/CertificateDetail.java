package com.cos.cercat.domain.certificate;

import java.util.List;

public record CertificateDetail(
        Certificate certificate,
        List<Subject> subjects
) {
    public static CertificateDetail of(Certificate certificate, List<Subject> subjects) {
        return new CertificateDetail(
                certificate,
                subjects
        );
    }
}
