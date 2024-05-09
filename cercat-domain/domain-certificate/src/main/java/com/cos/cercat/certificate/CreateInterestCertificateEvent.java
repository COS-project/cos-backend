package com.cos.cercat.certificate;

import com.cos.cercat.user.User;

import java.util.List;

public record CreateInterestCertificateEvent(
        User user,
        List<Certificate> certificateList
) {
    public static CreateInterestCertificateEvent of(User user, List<Certificate> certificateList) {
        return new CreateInterestCertificateEvent(user, certificateList);
    }
}
