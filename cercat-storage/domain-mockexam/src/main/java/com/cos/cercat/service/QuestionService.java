package com.cos.cercat.service;

import com.cos.cercat.domain.MockExamEntity;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.repository.QuestionJpaRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionJpaRepository questionJpaRepository;

    public List<QuestionEntity> getQuestionListByMockExam(MockExamEntity mockExamEntity) {
        return questionJpaRepository.findByMockExamEntity(mockExamEntity);
    }

    public QuestionEntity getQuestion(Long questionId) {
        return questionJpaRepository.findById(questionId).orElseThrow(()
                -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }

    public QuestionEntity getQuestion(MockExamEntity mockExamEntity, Integer questionSeq) {
        return questionJpaRepository.findQuestionByMockExamEntityAndQuestionSeq(mockExamEntity, questionSeq);
    }
}
