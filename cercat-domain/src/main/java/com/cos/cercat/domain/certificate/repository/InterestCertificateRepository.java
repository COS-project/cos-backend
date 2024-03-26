package com.cos.cercat.domain.certificate.repository;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.certificate.domain.InterestCertificate;
import com.cos.cercat.domain.certificate.domain.embededId.InterestCertificatePK;
import com.cos.cercat.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestCertificateRepository extends JpaRepository<InterestCertificate, InterestCertificatePK> {
    List<InterestCertificate> findInterestCertificatesByUser(User user);

    void deleteAllByUser(User user);

    List<InterestCertificate> findInterestCertificatesByCertificate(Certificate certificate);
}
