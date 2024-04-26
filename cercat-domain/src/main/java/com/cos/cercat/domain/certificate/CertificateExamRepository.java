package com.cos.cercat.domain.certificate;

public interface CertificateExamRepository {
    void save(TargetCertificate target, NewExamInformation newExamInformation);

    CertificateExam findRecentCertificateExam(Certificate certificate);
}
