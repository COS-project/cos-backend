package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.apis.mockExamResult.app.usecase.MockExamResultFetchUseCase;
import com.cos.cercat.apis.mockExamResult.dto.request.DateType;
import com.cos.cercat.apis.mockExamResult.dto.request.ReportType;
import com.cos.cercat.apis.mockExamResult.dto.response.*;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.DateCond;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "성적 리포트 조회 API")
public class MockExamResultFetchApi {


    private final MockExamResultFetchUseCase mockExamResultFetchUseCase;

    @GetMapping("/mock-exams/{mockExamId}")
    @Operation(summary = "성적 리포트 리스트 조회")
    public Response<List<MockExamResultWithSubjectsResponse>> getMockExamResults(@PathVariable Long mockExamId,
                                                                                 @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchUseCase.getMockExamResults(mockExamId, user.getId()));
    }

    @GetMapping("/certificates/{certificateId}/user-answers/wrong-answers")
    @Operation(summary = "전체 틀린문제 조회")
    public Response<Slice<UserAnswerResponse>> getAllWrongUserAnswers(@PageableDefault Pageable pageable,
                                                                      @PathVariable Long certificateId,
                                                                      @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchUseCase.getAllWrongUserAnswers(pageable, certificateId, user.getId()));
    }

    @GetMapping("/mock-exam-results/{mockExamResultId}/user-answers/wrong-answers")
    @Operation(summary = "특정 모의고사 틀린문제 조회")
    public Response<Slice<UserAnswerResponse>> getWrongUserAnswers(@PageableDefault() Pageable pageable,
                                                                   @PathVariable Long mockExamResultId,
                                                                   @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchUseCase.getWrongUserAnswers(pageable, mockExamResultId, user.getId()));
    }

    @GetMapping("/certificates/{certificateId}/mock-exam-results/average")
    @Operation(summary = "과목별 정답률 평균 및 머문 시간 평균 조회")
    public Response<List<SubjectResultsAVGResponse>> getSubjectResultsAVG(@PathVariable Long certificateId,
                                                                          @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchUseCase.getSubjectResultsAVG(certificateId, user.getId()));
    }

    @GetMapping("/certificates/{certificateId}/mock-exam-results/{reportType}/statistics")
    @Operation(summary = "주간/월간/년간 성적 리포트 통계 조회")
    public Response<Report> getReport(@PathVariable Long certificateId,
                                      @PathVariable ReportType reportType,
                                      DateCond dateCond,
                                      @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchUseCase.getReport(certificateId, reportType, dateCond, user.getId()));
    }

    @GetMapping("/certificates/{certificateId}/mock-exam-results/{dateType}")
    @Operation(summary = "날짜/주차/월 로 성적 리포트 조회")
    public Response<Slice<MockExamResultResponse>> getMockExamResultsByDate(@PathVariable Long certificateId,
                                                                            @PathVariable DateType dateType,
                                                                            DateCond dateCond,
                                                                            @AuthenticationPrincipal UserDTO user,
                                                                            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(mockExamResultFetchUseCase.getMockExamResultsByDate(certificateId, user.getId(), dateType, dateCond, pageable));
    }
}
