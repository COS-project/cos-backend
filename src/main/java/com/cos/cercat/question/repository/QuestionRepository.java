package com.cos.cercat.question.repository;

import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByMockExam(MockExam mockExam);

    Question findQuestionByMockExamAndQuestionSeq(MockExam mockExam, Integer sequence);

}
