package com.cos.cercat.mockExamResult.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.mockExamResult.app.MockExamResultFetchService;
import com.cos.cercat.mockExamResult.dto.response.MockExamResultResponse;
import com.cos.cercat.mockExamResult.dto.response.SubjectResultsAVGResponse;
import com.cos.cercat.mockExamResult.dto.response.UserAnswerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "모의고사 결과 조회 API")
public class MockExamResultFetchApi {


    private final MockExamResultFetchService mockExamResultFetchService;

    @GetMapping("/mock-exams/{mockExamId}")
    @Operation(summary = "모의고사 결과 리스트 조회")
    public Response<List<MockExamResultResponse>> getMockExamResults(@PathVariable Long mockExamId) {
        return Response.success(mockExamResultFetchService.getMockExamResults(mockExamId, 1L));
    }

    @GetMapping("/{certificateId}/user-answers/incorrect")
    @Operation(summary = "전체 틀린문제 조회")
    public Response<Slice<UserAnswerResponse>> getAllIncorrectUserAnswers(@PageableDefault Pageable pageable,
                                                                 @PathVariable Long certificateId) {
        return Response.success(mockExamResultFetchService.getAllInCorrectUserAnswers(pageable, certificateId, 1L));
    }

    @GetMapping("/mock-exam-results/{mockExamResultId}")
    @Operation(summary = "특정 모의고사 틀린문제 조회")
    public Response<Slice<UserAnswerResponse>> getIncorrectUserAnswers(@PageableDefault Pageable pageable,
                                                              @PathVariable Long mockExamResultId) {
        return Response.success(mockExamResultFetchService.getInCorrectUserAnswers(pageable, mockExamResultId));
    }

    //TODO: 모의고사 점수 일간 평균 API

    //TODO: 모의고사 점수 주간 평균 API

    //TODO: 모의고사 점수 월간 평균 API

    @GetMapping("/{certificateId}/mock-exam-results/average")
    @Operation(summary = "과목별 정답률 평균 및 머문 시간 평균 조회")
    public Response<List<SubjectResultsAVGResponse>> getSubjectResultsAVG(@PathVariable Long certificateId) {
        return Response.success(mockExamResultFetchService.getSubjectResultsAVG(certificateId, 1L));
    }

}
