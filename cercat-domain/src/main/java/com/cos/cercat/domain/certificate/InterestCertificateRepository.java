package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;

import java.util.List;

public interface InterestCertificateRepository {

    void saveAll(TargetUser user, InterestTargets interestTargets);

    List<InterestCertificate> findAll(TargetUser targetUser);

    void remove(TargetUser targetUser, TargetCertificate targetCertificate);

    void removeAll(TargetUser targetUser);
}
