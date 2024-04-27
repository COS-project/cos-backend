package com.cos.cercat.certificate;


import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;

import java.util.List;

public interface InterestCertificateRepository {

    void saveAll(User user, List<NewInterestCertificate> newInterestCertificates);

    List<InterestCertificate> find(User user);

    void remove(TargetUser targetUser, TargetCertificate targetCertificate);

    void removeAll(User user);
}
