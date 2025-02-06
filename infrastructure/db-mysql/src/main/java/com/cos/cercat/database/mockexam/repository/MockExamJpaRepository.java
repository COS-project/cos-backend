package com.cos.cercat.database.mockexam.repository;

import com.cos.cercat.database.mockexam.entity.MockExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MockExamJpaRepository extends JpaRepository<MockExamEntity, Long> {

    @Query("""
            SELECT m FROM MockExamEntity m
            WHERE m.certificateEntity.id = :certificateId AND m.examYear = :year
            """)
    List<MockExamEntity> findMockExamByCertificateIdAndExamYear(Long certificateId, Integer year);

    @Query("""
            SELECT m FROM MockExamEntity m
            WHERE m.certificateEntity.id = :certificateId AND m.examYear = :examYear AND m.round = :round
            """)
    Optional<MockExamEntity> findMockExamByCertificateIdAndExamYearAndRound(long certificateId, int examYear, int round);

    @Query("""
            SELECT m.examYear FROM MockExamEntity m
            WHERE m.certificateEntity.id = :certificateId
            GROUP BY m.examYear
            """)
    List<Integer> findExamYearsByCertificateId(Long certificateId);
}
