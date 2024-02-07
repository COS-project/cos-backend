package com.cos.cercat.certificate.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.InterestCertificate;
import com.cos.cercat.certificate.domain.embededId.InterestCertificatePK;
import com.cos.cercat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestCertificateRepository extends JpaRepository<InterestCertificate, InterestCertificatePK> {
    List<InterestCertificate> findInterestCertificatesByUser(User user);

    List<InterestCertificate> findInterestCertificatesByCertificate(Certificate certificate);

    void deleteAllByUser(User user);
}
