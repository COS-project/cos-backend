package com.cos.cercat.apis.certificate.dto.response;

import com.cos.cercat.domain.certificate.domain.InterestCertificate;
import com.cos.cercat.domain.certificate.domain.InterestPriority;

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
