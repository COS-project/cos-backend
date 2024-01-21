package com.cos.cercat.certificate.dto.request;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.InterestCertificate;
import com.cos.cercat.certificate.domain.InterestPriority;
import com.cos.cercat.user.domain.User;

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
