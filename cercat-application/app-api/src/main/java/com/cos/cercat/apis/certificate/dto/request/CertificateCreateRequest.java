package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.CertificateEntity;

import java.util.List;

public record CertificateCreateRequest(
        String certificateName,
        List<SubjectCreateRequest> subjectCreateRequests
) {
    public CertificateEntity toEntity() {
        return CertificateEntity.of(
                certificateName,
                subjectCreateRequests.stream()
                        .map(SubjectCreateRequest::toEntity)
                        .toList()
        );
    }
}
