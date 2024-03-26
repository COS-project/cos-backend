package com.cos.cercat.repository;

import com.cos.cercat.domain.MockExam;
import com.cos.cercat.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionBatchRepository {

    List<Question> findByMockExam(MockExam mockExam);

    Question findQuestionByMockExamAndQuestionSeq(MockExam mockExam, Integer sequence);

}
