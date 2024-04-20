package com.cos.cercat.apis.certificate.dto.request;

import com.cos.cercat.domain.certificate.SubjectInfo;

import java.util.List;

public record CertificateCreateRequest(
        String certificateName,
        List<SubjectInfo> subjectInfoList
) {
}
