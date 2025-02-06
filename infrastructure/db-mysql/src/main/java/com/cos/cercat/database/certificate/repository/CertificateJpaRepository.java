package com.cos.cercat.database.certificate.repository;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateJpaRepository extends JpaRepository<CertificateEntity, Long> {
    CertificateEntity findByCertificateName(String certificateName);
}
