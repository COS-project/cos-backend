package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.certificate.InterestTarget;
import com.cos.cercat.domain.certificate.InterestTargets;

import java.util.List;

public record InterestCertificateCreateRequest(
        List<InterestTarget> interestTargetList
) {
    public InterestTargets toInterestTargets() {
        return InterestTargets.from(
                interestTargetList
        );
    }
}
