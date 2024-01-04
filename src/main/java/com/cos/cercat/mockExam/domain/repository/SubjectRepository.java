package com.cos.cercat.mockExam.domain.repository;

import com.cos.cercat.mockExam.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
