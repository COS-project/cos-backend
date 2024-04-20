package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.InterestCertificateEntity;
import com.cos.cercat.domain.certificate.InterestPriority;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.certificate.InterestTarget;
import com.cos.cercat.domain.certificate.InterestTargets;

import java.util.List;

public record InterestCertificateUpdateRequest(
        List<InterestTarget> interestTargetList
) {
    public InterestTargets toInterestTargets() {
        return InterestTargets.from(interestTargetList);
    }
}
