package com.cos.cercat.domain.certificate.event.internal;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;

import java.util.List;

public record InterestCertificateCreatedEvent(
        User user,
        List<Certificate> certificateList
) {
    public static InterestCertificateCreatedEvent of(User user, List<Certificate> certificateList) {
        return new InterestCertificateCreatedEvent(user, certificateList);
    }
}
