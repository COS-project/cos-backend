package com.cos.cercat.certificate;

import com.cos.cercat.user.User;

import java.util.List;

public record InterestCertificateCreatedEvent(
        User user,
        List<Certificate> certificateList
) {
    public static InterestCertificateCreatedEvent of(User user, List<Certificate> certificateList) {
        return new InterestCertificateCreatedEvent(user, certificateList);
    }
}
