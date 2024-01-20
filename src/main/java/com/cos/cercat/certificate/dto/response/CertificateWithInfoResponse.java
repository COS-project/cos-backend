package com.cos.cercat.certificate.dto.response;

import com.cos.cercat.certificate.domain.Certificate;

public record CertificateWithInfoResponse(
        CertificateResponse certificate,
        ExamInfoResponse examInfo
) {
    public static CertificateWithInfoResponse from(Certificate entity) {
        return new CertificateWithInfoResponse(
                CertificateResponse.from(entity),
                ExamInfoResponse.from(entity.getExamInfo())
        );
    }

}
