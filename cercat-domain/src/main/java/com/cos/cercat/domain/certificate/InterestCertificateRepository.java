package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;

import java.util.List;

public interface InterestCertificateRepository {

    void saveAll(User user, List<NewInterestCertificate> newInterestCertificates);

    List<InterestCertificate> find(User user);

    void remove(TargetUser targetUser, TargetCertificate targetCertificate);

    void removeAll(User user);
}
