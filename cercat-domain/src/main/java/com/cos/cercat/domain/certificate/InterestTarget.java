package com.cos.cercat.domain.certificate;

public record InterestTarget(
        long certificateId,
        InterestPriority interestPriority
) {
}
