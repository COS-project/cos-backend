package com.cos.cercat.mockExam.domain.repository;

import com.cos.cercat.mockExam.domain.entity.MockExam;
import com.cos.cercat.mockExam.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByMockExam(MockExam mockExam);

}
