package com.cos.cercat.database.mockexam.repository;

import com.cos.cercat.database.mockexam.entity.MockExamEntity;
import com.cos.cercat.database.mockexam.entity.QuestionEntity;
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
