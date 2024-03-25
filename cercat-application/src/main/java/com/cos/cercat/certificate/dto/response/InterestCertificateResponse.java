package com.cos.cercat.certificate.dto.response;

import com.cos.cercat.certificate.domain.InterestCertificate;
import com.cos.cercat.certificate.domain.InterestPriority;

public record InterestCertificateResponse(
        CertificateResponse certificate,
        InterestPriority interestPriority
) {

    public static InterestCertificateResponse from(InterestCertificate entity) {
        return new InterestCertificateResponse(
                CertificateResponse.from(entity.getCertificate()),
                entity.getPriority()
        );
    }

}
