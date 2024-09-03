package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.entity.InterestCertificateEntity;
import com.cos.cercat.infra.entity.embededId.InterestCertificatePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterestCertificateJpaRepository extends JpaRepository<InterestCertificateEntity, InterestCertificatePK> {

    @Query("""
            select i from InterestCertificateEntity i
            where i.interestCertificatePK.userEntity.id = :userId
            """)
    List<InterestCertificateEntity> findInterestCertificatesByUserId(Long userId);

    @Modifying
    @Query("delete from InterestCertificateEntity i where i.interestCertificatePK.userEntity.id = :userId")
    void deleteAllByUserId(Long userId);

    @Query("""
            select i from InterestCertificateEntity i
            where i.interestCertificatePK.certificateEntity.id = :certificateId
            """)
    List<InterestCertificateEntity> findInterestCertificatesByCertificateId(Long certificateId);
}
