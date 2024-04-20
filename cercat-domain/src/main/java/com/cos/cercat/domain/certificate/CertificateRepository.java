package com.cos.cercat.domain.certificate;

import java.util.List;

public interface CertificateRepository {

    List<Certificate> findAll();

    void save(String certificateName, List<SubjectInfo> subjectsInfo);

    Certificate findById(TargetCertificate targetCertificate);
}
