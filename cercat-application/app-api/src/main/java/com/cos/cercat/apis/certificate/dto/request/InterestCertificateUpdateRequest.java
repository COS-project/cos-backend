package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.InterestCertificate;
import com.cos.cercat.domain.InterestPriority;
import com.cos.cercat.domain.User;

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
