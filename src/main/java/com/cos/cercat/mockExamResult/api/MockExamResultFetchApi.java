package com.cos.cercat.mockExamResult.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.mockExamResult.app.MockExamResultFetchService;
import com.cos.cercat.mockExamResult.dto.response.*;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "모의고사 결과 조회 API")
public class MockExamResultFetchApi {


    private final MockExamResultFetchService mockExamResultFetchService;

    @GetMapping("/mock-exams/{mockExamId}")
    @Operation(summary = "모의고사 결과 리스트 조회")
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

    @GetMapping("/{certificateId}/mock-exam-results/weekly-reports")
    @Operation(summary = "주간 성적 리포트 통계")
    public Response<Report<List<DailyScoreAVG>>> getWeeklyReport(@PathVariable Long certificateId,
                                                                 @AuthenticationPrincipal UserDTO user) {
        return Response.success(mockExamResultFetchService.getWeeklyReport(certificateId, user.getId()));
    }

    @GetMapping("{certificateId}/mock-exam-results/date")
    @Operation(summary = "해당하는 날짜의 모의고사 성적 조회")
    public Response<List<MockExamResultResponse>> getMockExamResultsByDate(@PathVariable Long certificateId,
                                                                                       @AuthenticationPrincipal UserDTO user,
                                                                                       @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        return Response.success(mockExamResultFetchService.getMockExamResultsByDate(certificateId, user.getId(), date));
    }
}
