package com.cos.cercat.domain.certificate;

import java.util.List;

public record InterestTargets(
        List<InterestTarget> interestTargetList
) {

    public static InterestTargets from(List<InterestTarget> interestTargets) {
        return new InterestTargets(interestTargets);
    }

    public List<TargetCertificate> certificates() {
        return interestTargetList.stream()
                .map(InterestTarget::certificateId)
                .map(TargetCertificate::from)
                .toList();
    }

}
