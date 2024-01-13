package com.cos.cercat.mockExam.api;

import com.cos.cercat.user.domain.User;
import com.cos.cercat.global.Response;
import com.cos.cercat.mockExam.dto.request.MockExamResultRequest;
import com.cos.cercat.mockExam.dto.request.MockExamSearchRequest;
import com.cos.cercat.mockExam.dto.response.MockExamWithResultResponse;
import com.cos.cercat.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.mockExam.app.service.MockExamServiceFacade;
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

        User user = new User(2L, "강지원2");

        List<MockExamWithResultResponse> responses = mockExamServiceFacade.getMockExamList(
                request.certificateId(),
                request.examYear(),
                user
        );

        return Response.success(responses);
    }

    @PostMapping("/{mockExamId}/results")
    @Operation(summary = "모의고사 결과 생성")
    public Response<Void> createMockExamResult(@PathVariable Long mockExamId,
                                               @RequestBody MockExamResultRequest request) {
        User user = new User(2L, "강지원2");
        mockExamServiceFacade.createMockExamResult(mockExamId, request, user);
        return Response.success("모의고사 결과 생성 완료");
    }

    @GetMapping("/{mockExamId}/questions")
    @Operation(summary = "모의고사 문제들 가져오기")
    public Response<List<QuestionResponse>> getQuestionList(@PathVariable Long mockExamId) {
        return Response.success(mockExamServiceFacade.getQuestionList(mockExamId));
    }
}
