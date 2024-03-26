package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.apis.mockExamResult.app.usecase.MockExamResultCreateUseCase;
import com.cos.cercat.apis.mockExamResult.dto.request.MockExamResultRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "성적 리포트 생성 API")
public class MockExamResultCreateApi {

    private final MockExamResultCreateUseCase mockExamResultCreateUseCase;

    @PostMapping("/mock-exams/{mockExamId}/mock-exam-results")
    @Operation(summary = "성적 리포트 생성 및 반환")
    public Response<Long> createMockExamResult(@PathVariable Long mockExamId,
                                                                             @RequestBody MockExamResultRequest request,
                                                                             @AuthenticationPrincipal UserDTO user) {

        return Response.success(mockExamResultCreateUseCase.createMockExamResult(mockExamId, request, user.getId()));
    }
}
