package com.cos.cercat.domain.certificate;

import java.time.LocalDateTime;
import java.util.List;

public interface CertificateExamRepository {
    void save(Certificate certificate, NewExamInformation newExamInformation);

    CertificateExam findPreviousCertificateExam(Certificate certificate);

    CertificateExam findNextCertificateExam(Certificate certificate);

    List<CertificateExam> findCertificateExamsByApplicationDate(LocalDateTime dateTime);

    List<CertificateExam> findCertificateExamsByDeadlineDate(LocalDateTime dateTime);
}
