package com.cos.cercat.mockExam.api;

import com.cos.cercat.common.Response;
import com.cos.cercat.mockExam.api.request.MockExamResultRequest;
import com.cos.cercat.mockExam.api.request.MockExamSearchRequest;
import com.cos.cercat.mockExam.api.response.MockExamWithResultResponse;
import com.cos.cercat.mockExam.app.service.MockExamResultService;
import com.cos.cercat.mockExam.app.service.MockExamService;
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

    private final MockExamService mockExamService;
    private final MockExamResultService mockExamResultService;

    @GetMapping
    @Operation(summary = "년도별 모의고사 가져오기")
    public Response<List<MockExamWithResultResponse>> getMockExamList(MockExamSearchRequest request) {
        List<MockExamWithResultResponse> responses = mockExamService.getMockExamList(request.certificateId(), request.examYear(), null);
        return Response.success(responses);
    }

    @PostMapping("/{mockExamId}/results")
    @Operation(summary = "모의고사 결과 생성")
    public Response<Void> createMockExamResult(@PathVariable Long mockExamId,
                                               @RequestBody MockExamResultRequest request) {
        mockExamResultService.createMockExamResult(mockExamId, request, null);
        return Response.success("success create mock exam");
    }
}
