package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.MockExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MockExamJpaRepository extends JpaRepository<MockExamEntity, Long> {

    List<MockExamEntity> findMockExamByCertificateEntityAndExamYear(CertificateEntity certificateEntity, Integer year);

    @Query("""
            SELECT m FROM MockExamEntity m
            WHERE m.certificateEntity.id = :certificateId AND m.examYear = :examYear AND m.round = :round
            """)
    Optional<MockExamEntity> findMockExamByCertificateIdAndExamYearAndRound(long certificateId, int examYear, int round);

    @Query("""
            SELECT m.examYear FROM MockExamEntity m
            WHERE m.certificateEntity.id = :certificateId
            GROUP BY m.examYear
            ORDER BY m.examYear
            """)
    List<Integer> findMockExamYearsByCertificate(@Param("certificateId") Long certificateId);

    @Query("""
            SELECT m.round FROM MockExamEntity m
            WHERE m.certificateEntity.id = :certificateId AND m.examYear = :examYear
            ORDER BY m.round
            """)
    List<Integer> findMockExamRoundsByCertificateEntityAndExamYear(@Param("certificateId") Long certificateId, @Param("examYear") Integer examYear);
}
