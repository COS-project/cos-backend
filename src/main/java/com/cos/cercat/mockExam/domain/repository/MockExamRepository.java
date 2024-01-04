package com.cos.cercat.mockExam.domain.repository;

import com.cos.cercat.mockExam.domain.entity.MockExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockExamRepository extends JpaRepository<MockExam, Long> {

    List<MockExam> findMockExamByCertificate_IdAndExamYear(Long certificateId, Integer year);

}
