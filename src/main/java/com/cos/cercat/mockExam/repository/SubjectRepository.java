package com.cos.cercat.mockExam.repository;

import com.cos.cercat.mockExam.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
