package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateJpaRepository extends JpaRepository<CertificateEntity, Long> {
    CertificateEntity findByCertificateName(String certificateName);
}
