package com.cos.cercat.certificate;

public interface CertificateExamRepository {
    void save(TargetCertificate target, NewExamInformation newExamInformation);

    CertificateExam findRecentCertificateExam(Certificate certificate);
}
