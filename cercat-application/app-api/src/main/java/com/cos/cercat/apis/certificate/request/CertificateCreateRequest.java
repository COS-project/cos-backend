package com.cos.cercat.apis.certificate.request;


import com.cos.cercat.certificate.SubjectInfo;

import java.util.List;

public record CertificateCreateRequest(
        String certificateName,
        List<SubjectInfo> subjectInfoList
) {
}
