package com.cos.cercat.domain.certificate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CertificateExamRepository {
    void save(Certificate certificate, NewExamInformation newExamInformation);

    Optional<CertificateExam> findPreviousCertificateExam(Certificate certificate);

    Optional<CertificateExam> findNextCertificateExam(Certificate certificate);

    List<CertificateExam> findCertificateExamsByApplicationDate(LocalDateTime dateTime);

    List<CertificateExam> findCertificateExamsByDeadlineDate(LocalDateTime dateTime);
}
