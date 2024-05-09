package com.cos.cercat.certificate;

public interface CertificateExamRepository {
    void save(Certificate certificate, NewExamInformation newExamInformation);

    CertificateExam findRecentCertificateExam(Certificate certificate);
}
