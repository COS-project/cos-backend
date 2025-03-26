package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.apis.mockExamResult.response.*;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.apis.global.annotation.CursorDefault;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.PageResult;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.mockexam.MockExamId;
import com.cos.cercat.domain.mockexamresult.*;

import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class ReadMockExamResultApi implements ReadMockExamResultApiDocs {

  private final ReadMockExamResultService readMockExamResultService;

  @GetMapping("/mock-exams/{mockExamId}")
  public Response<List<MockExamResultWithSubjectsResponse>> mockExamResultDetails(
      @PathVariable Long mockExamId,
      @AuthenticationPrincipal User user) {
    List<MockExamResultDetail> mockExamResultDetails = readMockExamResultService.getMockExamResultDetail(
        MockExamId.from(mockExamId), UserId.from(user.getId()));
    List<MockExamResultWithSubjectsResponse> responses = mockExamResultDetails.stream()
        .map(MockExamResultWithSubjectsResponse::from)
        .toList();
    return Response.success(responses);
  }

  @GetMapping("/mock-exams/{mockExamId}/mock-exam-results/recent")
  public Response<MockExamResultResponse> recentMockExamResult(@PathVariable Long mockExamId,
      @AuthenticationPrincipal User currentUser) {
    MockExamResult recentMockExamResult = readMockExamResultService.getRecentMockExamResult(
        MockExamId.from(mockExamId),
        UserId.from(currentUser.getId())
    );

    return Response.success(MockExamResultResponse.from(recentMockExamResult));
  }


  @GetMapping("/certificates/{certificateId}/user-answers/wrong-answers")
  public Response<SliceResult<UserAnswerResponse>> allWrongUserAnswers(@CursorDefault Cursor cursor,
      @PathVariable Long certificateId,
      @AuthenticationPrincipal User currentUser) {

    SliceResult<UserAnswer> allWrongUserAnswers = readMockExamResultService.getAllWrongUserAnswers(
        CertificateId.from(certificateId),
        UserId.from(currentUser.getId()),
        cursor);

    return Response.success(allWrongUserAnswers.map(UserAnswerResponse::from));
  }

  @GetMapping("/mock-exam-results/{mockExamResultId}/user-answers/wrong-answers")
  public Response<SliceResult<UserAnswerResponse>> wrongUserAnswers(@CursorDefault Cursor cursor,
      @PathVariable Long mockExamResultId,
      @AuthenticationPrincipal User currentUser) {
    SliceResult<UserAnswerResponse> responses = readMockExamResultService.getWrongUserAnswers(
        MockExamResultId.from(mockExamResultId),
        UserId.from(currentUser.getId()),
        cursor
    ).map(UserAnswerResponse::from);

    return Response.success(responses);
  }

  @GetMapping("/certificates/{certificateId}/mock-exam-results/average")
  public Response<List<SubjectResultsAVGResponse>> subjectResultsAVG(
      @PathVariable Long certificateId,
      @AuthenticationPrincipal User currentUser) {
    List<SubjectResultStatistics> subjectResultsStatistics = readMockExamResultService.getSubjectResultsStatistics(
        UserId.from(currentUser.getId()),
        CertificateId.from(certificateId)
    );

    List<SubjectResultsAVGResponse> responses = subjectResultsStatistics.stream()
        .map(SubjectResultsAVGResponse::from)
        .toList();

    return Response.success(responses);
  }

  @GetMapping("/certificates/{certificateId}/mock-exam-result/{reportType}/statistics")
  public Response<ReportResponse> scoreReport(@PathVariable Long certificateId,
      @PathVariable ReportType reportType,
      DateCond dateCond,
      @AuthenticationPrincipal User currentUser) {
    List<ScoreData> reportData = readMockExamResultService.findReportData(
        UserId.from(currentUser.getId()),
        CertificateId.from(certificateId),
        reportType,
        dateCond
    );

    return Response.success(ReportResponse.from(reportData));
  }

  @GetMapping("/certificates/{certificateId}/mock-exam-results/{dateType}")
  public Response<PageResult<MockExamResultResponse>> mockExamResults(
      @PathVariable Long certificateId,
      @PathVariable DateType dateType,
      DateCond dateCond,
      @AuthenticationPrincipal User currentUser,
      @CursorDefault Cursor cursor) {
    PageResult<MockExamResultResponse> responses = readMockExamResultService.findMockExamResults(
            UserId.from(currentUser.getId()),
            CertificateId.from(certificateId),
            dateType,
            dateCond,
            cursor)
        .map(MockExamResultResponse::from);
    return Response.success(responses);
  }


}
