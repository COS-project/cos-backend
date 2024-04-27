package com.cos.cercat.certificate;

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

    public List<NewInterestCertificate> toNewInterestCertificates(List<Certificate> certificates) {
        return interestTargetList.stream()
                .map(interestTarget -> {
                    Certificate certificate = certificates.stream()
                            .filter(c -> c.id() == interestTarget.certificateId())
                            .findFirst()
                            .get();
                    return new NewInterestCertificate(
                            certificate,
                            interestTarget.interestPriority()
                    );
                }).toList();
    }

}
