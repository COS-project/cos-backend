package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.apis.mockExamResult.app.usecase.MockExamResultUpdateUseCase;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "성적 리포트 수정 API")
public class MockExamResultUpdateApi {

    private final MockExamResultUpdateUseCase mockExamResultUpdateUseCase;

    @PatchMapping("/user-answers/{userAnswerId}/review")
    @Operation(summary = "틀린 문제 더이상 보지 않기")
    public Response<Void> reviewUserAnswers(@PathVariable Long userAnswerId,
                                            @AuthenticationPrincipal UserDTO user) {
        mockExamResultUpdateUseCase.reviewUserAnswers(userAnswerId, user.getId());
        return Response.success("틀린 문제 리뷰 성공");
    }
}
