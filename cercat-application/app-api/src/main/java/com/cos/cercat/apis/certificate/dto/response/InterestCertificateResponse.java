package com.cos.cercat.apis.certificate.dto.response;

import com.cos.cercat.domain.certificate.InterestCertificate;
import com.cos.cercat.domain.certificate.InterestPriority;

public record InterestCertificateResponse(
        CertificateResponse certificate,
        InterestPriority interestPriority
) {

    public static InterestCertificateResponse from(InterestCertificate interestCertificate) {
        return new InterestCertificateResponse(
                CertificateResponse.from(interestCertificate.certificate()),
                interestCertificate.interestPriority()
        );
    }

}
