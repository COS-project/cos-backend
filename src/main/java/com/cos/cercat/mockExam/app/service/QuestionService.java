package com.cos.cercat.mockExam.app.service;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.mockExam.domain.entity.MockExam;
import com.cos.cercat.mockExam.domain.entity.Question;
import com.cos.cercat.mockExam.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getQuestionListByMockExam(MockExam mockExam) {
        return questionRepository.findByMockExam(mockExam);
    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(()
                -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }
}
