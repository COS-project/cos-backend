package com.cos.cercat.domain.certificate;


import com.cos.cercat.domain.user.User;

public record InterestCertificate(
        User user,
        Certificate certificate,
        InterestPriority interestPriority
) {

}
