package com.cos.cercat.mockExam.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.mockExam.dto.request.MockExamResultRequest;
import com.cos.cercat.mockExam.dto.request.MockExamSearchRequest;
import com.cos.cercat.mockExam.dto.response.MockExamWithResultResponse;
import com.cos.cercat.question.dto.response.QuestionResponse;
import com.cos.cercat.mockExam.app.MockExamServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/mock-exams")
public class MockExamController {

    private final MockExamServiceFacade mockExamServiceFacade;

    @GetMapping
    @Operation(summary = "년도별 모의고사 가져오기")
    public Response<List<MockExamWithResultResponse>> getMockExamList(MockExamSearchRequest request) {
        List<MockExamWithResultResponse> responses = mockExamServiceFacade.getMockExamList(
                request.certificateId(),
                request.examYear(),
                1L
        );

        return Response.success(responses);
    }

    @PostMapping("/{mockExamId}/results")
    @Operation(summary = "모의고사 결과 생성")
    public Response<Void> createMockExamResult(@PathVariable Long mockExamId,
                                               @RequestBody MockExamResultRequest request) {
        mockExamServiceFacade.createMockExamResult(mockExamId, request, 1L);
        return Response.success("모의고사 결과 생성 완료");
    }


}
