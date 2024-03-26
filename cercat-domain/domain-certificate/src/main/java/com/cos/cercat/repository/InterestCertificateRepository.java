package com.cos.cercat.repository;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.InterestCertificate;
import com.cos.cercat.domain.User;
import com.cos.cercat.domain.embededId.InterestCertificatePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestCertificateRepository extends JpaRepository<InterestCertificate, InterestCertificatePK> {
    List<InterestCertificate> findInterestCertificatesByUser(User user);

    void deleteAllByUser(User user);

    List<InterestCertificate> findInterestCertificatesByCertificate(Certificate certificate);
}
