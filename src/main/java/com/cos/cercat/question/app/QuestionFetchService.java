package com.cos.cercat.question.app;

import com.cos.cercat.mockExam.app.MockExamService;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.question.dto.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionFetchService {

    private final MockExamService mockExamService;
    private final QuestionService questionService;

    /**
     * 모의고사 ID를 통해 특정 모의고사의 모든 문제들을 가져온다.
     *
     * @param mockExamId 모의고사 고유 ID
     * */
    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestionList(Long mockExamId) {

        MockExam mockExam = mockExamService.getMockExamById(mockExamId);

        return questionService.getQuestionListByMockExam(mockExam).stream()
                .map(QuestionResponse::from)
                .toList();
    }

}
