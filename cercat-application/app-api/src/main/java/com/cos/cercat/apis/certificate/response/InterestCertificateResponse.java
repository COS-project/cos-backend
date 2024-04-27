package com.cos.cercat.apis.certificate.response;

import com.cos.cercat.certificate.InterestCertificate;
import com.cos.cercat.certificate.InterestPriority;

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
