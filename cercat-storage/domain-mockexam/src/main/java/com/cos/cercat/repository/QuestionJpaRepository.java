package com.cos.cercat.repository;

import com.cos.cercat.domain.MockExamEntity;
import com.cos.cercat.domain.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, Long>, QuestionBatchRepository {

    List<QuestionEntity> findByMockExamEntity(MockExamEntity mockExamEntity);

    QuestionEntity findQuestionByMockExamEntityAndQuestionSeq(MockExamEntity mockExamEntity, Integer sequence);

}
