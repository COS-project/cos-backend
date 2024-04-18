package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.InterestCertificate;
import com.cos.cercat.domain.InterestPriority;
import com.cos.cercat.domain.UserEntity;

public record InterestCertificateCreateRequest(
        Long certificateId,
        InterestPriority interestPriority
) {
    public InterestCertificate toEntity(CertificateEntity certificateEntity, UserEntity userEntity) {
        return new InterestCertificate(
                certificateEntity,
                userEntity,
                interestPriority
        );
    }
}
