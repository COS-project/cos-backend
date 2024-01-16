package com.cos.cercat.question.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.question.app.QuestionFetchService;
import com.cos.cercat.question.dto.response.QuestionResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QuestionFetchApi {

    private final QuestionFetchService questionFetchService;

    @GetMapping("mock-exams/{mockExamId}/questions")
    @Operation(summary = "모의고사 문제들 가져오기")
    public Response<List<QuestionResponse>> getQuestionList(@PathVariable Long mockExamId) {

        return Response.success(questionFetchService.getQuestionList(mockExamId));
    }

}
