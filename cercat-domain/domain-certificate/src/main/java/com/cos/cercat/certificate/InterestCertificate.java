package com.cos.cercat.certificate;


import com.cos.cercat.user.User;

public record InterestCertificate(
        User user,
        Certificate certificate,
        InterestPriority interestPriority
) {

}
