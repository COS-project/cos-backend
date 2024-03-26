package com.cos.cercat.domain.certificate.repository;

import com.cos.cercat.domain.certificate.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Certificate findByCertificateName(String certificateName);
}
