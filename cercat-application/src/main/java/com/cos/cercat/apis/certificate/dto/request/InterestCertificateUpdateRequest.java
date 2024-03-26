package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.certificate.domain.InterestCertificate;
import com.cos.cercat.domain.certificate.domain.InterestPriority;
import com.cos.cercat.domain.user.domain.User;

public record InterestCertificateUpdateRequest(
        Long certificateId,
        InterestPriority interestPriority
) {
    public InterestCertificate toEntity(Certificate certificate, User user) {
        return new InterestCertificate(
                certificate,
                user,
                interestPriority
        );
    }
}
