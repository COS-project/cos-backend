package com.cos.cercat.repository;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.MockExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockExamRepository extends JpaRepository<MockExam, Long> {

    List<MockExam> findMockExamByCertificateAndExamYear(Certificate certificate, Integer year);

    MockExam findMockExamByCertificateAndExamYearAndRound(Certificate certificate, Integer examYear, Integer round);

    @Query("""
            SELECT m.examYear FROM MockExam m
            WHERE m.certificate.id = :certificateId
            GROUP BY m.examYear
            ORDER BY m.examYear
            """)
    List<Integer> findMockExamYearsByCertificate(@Param("certificateId") Long certificateId);

    @Query("""
            SELECT m.round FROM MockExam m
            WHERE m.certificate.id = :certificateId AND m.examYear = :examYear
            ORDER BY m.round
            """)
    List<Integer> findMockExamRoundsByCertificateAndExamYear(@Param("certificateId") Long certificateId, @Param("examYear") Integer examYear);
}
