package com.cos.cercat.mockExam.api;


import com.cos.cercat.global.Response;
import com.cos.cercat.mockExam.app.MockExamFetchService;
import com.cos.cercat.mockExam.dto.request.MockExamSearchRequest;
import com.cos.cercat.mockExam.dto.response.MockExamWithResultResponse;
import com.cos.cercat.mockExam.dto.response.QuestionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "모의고사 조회 API")
public class MockExamFetchApi {

    private final MockExamFetchService mockExamFetchService;

    @GetMapping("/{certificateId}/mock-exams")
    @Operation(summary = "년도별 모의고사 가져오기")
    public Response<List<MockExamWithResultResponse>> getMockExamList(@PathVariable Long certificateId,
                                                                      Integer examYear) {
        List<MockExamWithResultResponse> responses = mockExamFetchService.getMockExamList(
                certificateId,
                examYear,
                1L
        );

        return Response.success(responses);
    }

    @GetMapping("mock-exams/{mockExamId}/questions")
    @Operation(summary = "모의고사 문제들 가져오기")
    public Response<List<QuestionResponse>> getQuestionList(@PathVariable Long mockExamId) {

        return Response.success(mockExamFetchService.getQuestionList(mockExamId));
    }

}
