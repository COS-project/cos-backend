package com.cos.cercat.apis.certificate.response;


import com.cos.cercat.domain.certificate.CertificateExam;

public record CertificateExamResponse(
        CertificateResponse certificate,
        ExamInfoResponse examInfo
) {
    public static CertificateExamResponse from(CertificateExam certificateExam) {
        return new CertificateExamResponse(
                CertificateResponse.from(certificateExam.certificate()),
                ExamInfoResponse.from(certificateExam.examInformation())
        );
    }
}
