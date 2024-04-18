package com.cos.cercat.domain.certificate;

import java.util.List;

public interface CertificateRepository {

    List<Certificate> findAll();

    void append(NewCertificate newCertificate);
}
