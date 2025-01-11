package com.cos.cercat.apis.certificate.request;


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
