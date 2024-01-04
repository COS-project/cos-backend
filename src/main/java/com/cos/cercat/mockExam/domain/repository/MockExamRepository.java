package com.cos.cercat.mockExam.domain.repository;

import com.cos.cercat.mockExam.domain.entity.MockExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MockExamRepostiory extends JpaRepository<MockExam, Long> {
}
