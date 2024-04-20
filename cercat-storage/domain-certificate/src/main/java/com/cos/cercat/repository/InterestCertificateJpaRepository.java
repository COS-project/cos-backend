package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.InterestCertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.embededId.InterestCertificatePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterestCertificateJpaRepository extends JpaRepository<InterestCertificateEntity, InterestCertificatePK> {
    List<InterestCertificateEntity> findInterestCertificatesByUserEntity(UserEntity userEntity);

    @Modifying
    @Query("delete from InterestCertificateEntity i where i.userEntity.id = :userId")
    void deleteAllByUserId(Long userId);

    List<InterestCertificateEntity> findInterestCertificatesByCertificateEntity(CertificateEntity certificateEntity);
}
