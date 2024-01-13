package com.cos.cercat.mockExam.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExam.domain.MockExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockExamRepository extends JpaRepository<MockExam, Long> {

    List<MockExam> findMockExamByCertificateAndExamYear(Certificate certificate, Integer year);

    MockExam findMockExamByCertificateAndExamYearAndRound(Certificate certificate, Integer examYear, Integer round);

}
