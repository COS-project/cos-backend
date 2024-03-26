package com.cos.cercat.repository;

import com.cos.cercat.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Certificate findByCertificateName(String certificateName);
}
