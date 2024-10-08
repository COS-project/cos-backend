package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.entity.MockExamEntity;
import com.cos.cercat.infra.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, Long>, QuestionBatchRepository {

    @Query("""
            SELECT q from QuestionEntity q
            where q.mockExamEntity.id = :mockExamId
           """)
    List<QuestionEntity> findByMockExamId(Long mockExamId);

    QuestionEntity findQuestionByMockExamEntityAndQuestionSeq(MockExamEntity mockExamEntity, Integer sequence);

}
