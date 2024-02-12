package com.cos.cercat.mockExamResult.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.mockExamResult.dto.request.MockExamResultRequest;
import com.cos.cercat.mockExamResult.app.MockExamResultCreateService;
import com.cos.cercat.mockExamResult.dto.response.MockExamResultWithSubjectsResponse;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "모의고사 결과 생성 API")
public class MockExamResultCreateApi {

    private final MockExamResultCreateService mockExamResultCreateService;

    @PostMapping("/mock-exams/{mockExamId}/results")
    @Operation(summary = "모의고사 결과 생성 및 반환")
    public Response<MockExamResultWithSubjectsResponse> createMockExamResult(@PathVariable Long mockExamId,
                                                                             @RequestBody MockExamResultRequest request,
                                                                             @AuthenticationPrincipal UserDTO user) {

        return Response.success(mockExamResultCreateService.createMockExamResult(mockExamId, request, user.getId()));
    }
}
