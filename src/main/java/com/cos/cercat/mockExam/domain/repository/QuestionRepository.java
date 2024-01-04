package com.cos.cercat.mockExam.domain.repository;

import com.cos.cercat.mockExam.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
