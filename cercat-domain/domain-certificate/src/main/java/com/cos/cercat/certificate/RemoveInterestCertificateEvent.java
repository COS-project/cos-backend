package com.cos.cercat.certificate;

import com.cos.cercat.user.User;

import java.util.List;

public record RemoveInterestCertificateEvent(
        User user,
        List<Certificate> certificateList
) {
    public static RemoveInterestCertificateEvent of(User user, List<Certificate> certificateList) {
        return new RemoveInterestCertificateEvent(user, certificateList);
    }
}
