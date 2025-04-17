package com.cos.cercat.domain.certificate.event.internal;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;

import java.util.List;

public record InterestCertificateRemovedEvent(
        User user,
        List<Certificate> certificateList
) {
    public static InterestCertificateRemovedEvent of(User user, List<Certificate> certificateList) {
        return new InterestCertificateRemovedEvent(user, certificateList);
    }
}
