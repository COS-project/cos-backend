package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.InterestCertificate;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.embededId.InterestCertificatePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestCertificateRepository extends JpaRepository<InterestCertificate, InterestCertificatePK> {
    List<InterestCertificate> findInterestCertificatesByUserEntity(UserEntity userEntity);

    void deleteAllByUserEntity(UserEntity userEntity);

    List<InterestCertificate> findInterestCertificatesByCertificateEntity(CertificateEntity certificateEntity);
}
