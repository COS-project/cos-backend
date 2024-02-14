package com.cos.cercat.mockExamResult.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.mockExamResult.app.MockExamResultFetchService;
import com.cos.cercat.mockExamResult.dto.request.DateQueryParam;
import com.cos.cercat.mockExamResult.dto.request.DateType;
import com.cos.cercat.mockExamResult.dto.request.ReportType;
import com.cos.cercat.mockExamResult.dto.response.*;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "성적 리포트 조회 API")
public class MockExamResultFetchApi {


    private final MockExamResultFetchService mockExamResultFetchService;

    @GetMapping("/mock-exams/{mockExamId}")
    @Operation(summary = "성적 리포트 리스트 조회")
    public Response<List<MockExamResultWithSubjectsResponse>> getMockExamResults(@PathVariable Long mockExamId,
                                                                                 @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchService.getMockExamResults(mockExamId, user.getId()));
    }

    @GetMapping("/{certificateId}/user-answers/incorrect")
    @Operation(summary = "전체 틀린문제 조회")
    public Response<Slice<UserAnswerResponse>> getAllIncorrectUserAnswers(@PageableDefault Pageable pageable,
                                                                          @PathVariable Long certificateId,
                                                                          @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchService.getAllInCorrectUserAnswers(pageable, certificateId, user.getId()));
    }

    @GetMapping("/mock-exam-results/{mockExamResultId}")
    @Operation(summary = "특정 모의고사 틀린문제 조회")
    public Response<Slice<UserAnswerResponse>> getIncorrectUserAnswers(@PageableDefault Pageable pageable,
                                                                       @PathVariable Long mockExamResultId) {
        return Response.success(mockExamResultFetchService.getInCorrectUserAnswers(pageable, mockExamResultId));
    }

    @GetMapping("/{certificateId}/mock-exam-results/average")
    @Operation(summary = "과목별 정답률 평균 및 머문 시간 평균 조회")
    public Response<List<SubjectResultsAVGResponse>> getSubjectResultsAVG(@PathVariable Long certificateId,
                                                                          @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchService.getSubjectResultsAVG(certificateId, user.getId()));
    }

    @GetMapping("/{certificateId}/mock-exam-results/{reportType}/reports")
    @Operation(summary = "성적 리포트 통계")
    public Response<Report> getReport(@PathVariable Long certificateId,
                                      @PathVariable ReportType reportType,
                                      DateQueryParam dateQueryParam,
                                      @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchService.getReport(certificateId, reportType, dateQueryParam, user.getId()));
    }

    @GetMapping("{certificateId}/mock-exam-results/{dateType}")
    @Operation(summary = "날짜/주차/월 로 성적 리포트 조회")
    public Response<Slice<MockExamResultResponse>> getMockExamResultsByDate(@PathVariable Long certificateId,
                                                                            @PathVariable DateType dateType,
                                                                            DateQueryParam dateQueryParam,
                                                                            @AuthenticationPrincipal UserDTO user,
                                                                            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(mockExamResultFetchService.getMockExamResultsByDate(certificateId, user.getId(), dateType, dateQueryParam, pageable));
    }
}
