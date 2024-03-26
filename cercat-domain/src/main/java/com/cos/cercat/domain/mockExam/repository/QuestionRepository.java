package com.cos.cercat.domain.mockExam.repository;

import com.cos.cercat.domain.mockExam.domain.MockExam;
import com.cos.cercat.domain.mockExam.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionBatchRepository {

    List<Question> findByMockExam(MockExam mockExam);

    Question findQuestionByMockExamAndQuestionSeq(MockExam mockExam, Integer sequence);

}
