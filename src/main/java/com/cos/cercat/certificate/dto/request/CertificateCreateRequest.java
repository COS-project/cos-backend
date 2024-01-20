package com.cos.cercat.certificate.dto.request;

import com.cos.cercat.certificate.domain.Certificate;

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
