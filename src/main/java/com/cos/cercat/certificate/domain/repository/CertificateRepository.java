package com.cos.cercat.mockExam.domain.repository;

import com.cos.cercat.mockExam.domain.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Certificate findByCertificateName(String certificateName);
}
