package com.cos.cercat.mockExamResult.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.mockExamResult.dto.request.MockExamResultRequest;
import com.cos.cercat.mockExamResult.app.MockExamResultCreateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MockExamResultCreateApi {

    private final MockExamResultCreateService mockExamResultCreateService;



    @PostMapping("/mock-exams/{mockExamId}/results")
    @Operation(summary = "모의고사 결과 생성")
    public Response<Void> createMockExamResult(@PathVariable Long mockExamId,
                                               @RequestBody MockExamResultRequest request) {
        mockExamResultCreateService.createMockExamResult(mockExamId, request, 1L);
        return Response.success("모의고사 결과 생성 완료");
    }
}
