package com.cos.cercat.domain.certificate;

public interface CertificateExamRepository {
    void save(TargetCertificate target, ExamInformation examInformation);

    CertificateExam findRecentCertificateExam(TargetCertificate targetCertificate);
}
