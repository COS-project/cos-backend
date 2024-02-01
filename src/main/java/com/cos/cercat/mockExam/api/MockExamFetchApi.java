package com.cos.cercat.mockExam.api;


import com.cos.cercat.global.Response;
import com.cos.cercat.mockExam.app.MockExamFetchService;
import com.cos.cercat.mockExam.dto.request.MockExamSearchRequest;
import com.cos.cercat.mockExam.dto.response.ExamYearWithRoundsResponse;
import com.cos.cercat.mockExam.dto.response.MockExamWithResultResponse;
import com.cos.cercat.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                                                                      Integer examYear,
                                                                      @AuthenticationPrincipal UserDTO user) {
        List<MockExamWithResultResponse> responses = mockExamFetchService.getMockExamList(
                certificateId,
                examYear,
                user.getId()
        );

        return Response.success(responses);
    }

    @GetMapping("mock-exams/{mockExamId}/questions")
    @Operation(summary = "모의고사 문제들 가져오기")
    public Response<List<QuestionResponse>> getQuestionList(@PathVariable Long mockExamId) {

        return Response.success(mockExamFetchService.getQuestionList(mockExamId));
    }

    @GetMapping("/{certificateId}/mock-exam-infos")
    @Operation(summary = "자격증 ID를 통해 해당 자격증 모의고사의 응시년도 및 회차정보 조회")
    public Response<ExamYearWithRoundsResponse> getMockExamInfos(@PathVariable Long certificateId) {
        return Response.success(mockExamFetchService.getMockExamInfoList(certificateId));
    }

}
