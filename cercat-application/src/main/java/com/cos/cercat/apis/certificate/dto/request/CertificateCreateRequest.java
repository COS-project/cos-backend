package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.certificate.domain.Certificate;

import java.util.List;

public record CertificateCreateRequest(
        String certificateName,
        List<SubjectCreateRequest> subjectCreateRequests
) {
    public Certificate toEntity() {
        return new Certificate(
                certificateName,
                subjectCreateRequests.stream()
                        .map(SubjectCreateRequest::toEntity)
                        .toList()
        );
    }
}
