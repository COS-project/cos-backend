package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.apis.mockExamResult.response.*;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.PageResult;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.mockexamresult.*;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class ReadMockExamResultApi implements ReadMockExamResultApiDocs {

    private final ReadMockExamResultService readMockExamResultService;

    @GetMapping("/mock-exams/{mockExamId}")
    public Response<List<MockExamResultWithSubjectsResponse>> mockExamResultDetails(@PathVariable Long mockExamId,
                                                                                    @AuthenticationPrincipal User user) {
        List<MockExamResultDetail> mockExamResultDetails = readMockExamResultService.getMockExamResultDetail(TargetMockExam.from(mockExamId), TargetUser.from(user.getId()));
        List<MockExamResultWithSubjectsResponse> responses = mockExamResultDetails.stream()
                .map(MockExamResultWithSubjectsResponse::from)
                .toList();
        return Response.success(responses);
    }

    @GetMapping("/mock-exams/{mockExamId}/mock-exam-results/recent")
    public Response<MockExamResultResponse> recentMockExamResult(@PathVariable Long mockExamId,
                                                                 @AuthenticationPrincipal User currentUser) {
        MockExamResult recentMockExamResult = readMockExamResultService.getRecentMockExamResult(
                TargetMockExam.from(mockExamId),
                TargetUser.from(currentUser.getId())
        );

        return Response.success(MockExamResultResponse.from(recentMockExamResult));
    }



    @GetMapping("/certificates/{certificateId}/user-answers/wrong-answers")
    public Response<SliceResult<UserAnswerResponse>> allWrongUserAnswers(Cursor cursor,
                                                                         @PathVariable Long certificateId,
                                                                         @AuthenticationPrincipal User currentUser) {
        SliceResult<UserAnswer> allWrongUserAnswers = readMockExamResultService.getAllWrongUserAnswers(
                TargetCertificate.from(certificateId),
                TargetUser.from(currentUser.getId()),
                cursor);

        return Response.success(allWrongUserAnswers.map(UserAnswerResponse::from));
    }

    @GetMapping("/mock-exam-results/{mockExamResultId}/user-answers/wrong-answers")
    public Response<SliceResult<UserAnswerResponse>> wrongUserAnswers(Cursor cursor,
                                                                      @PathVariable Long mockExamResultId,
                                                                      @AuthenticationPrincipal User currentUser) {
        SliceResult<UserAnswerResponse> responses = readMockExamResultService.getWrongUserAnswers(
                TargetMockExamResult.from(mockExamResultId),
                TargetUser.from(currentUser.getId()),
                cursor
        ).map(UserAnswerResponse::from);

        return Response.success(responses);
    }

    @GetMapping("/certificates/{certificateId}/mock-exam-results/average")
    public Response<List<SubjectResultsAVGResponse>> subjectResultsAVG(@PathVariable Long certificateId,
                                                                       @AuthenticationPrincipal User currentUser) {
        List<SubjectResultsAVGResponse> responses = readMockExamResultService.getSubjectResultsStatistics(
                TargetUser.from(currentUser.getId()),
                TargetCertificate.from(certificateId)
                ).stream()
                .map(SubjectResultsAVGResponse::from)
                .toList();

        return Response.success(responses);
    }

    @GetMapping("/certificates/{certificateId}/mock-exam-result")
    public Response<ReportResponse> scoreReport(@PathVariable Long certificateId,
                                                @PathVariable ReportType reportType,
                                                DateCond dateCond,
                                                @AuthenticationPrincipal User currentUser) {
        List<ScoreData> reportData = readMockExamResultService.findReportData(
                TargetUser.from(currentUser.getId()),
                TargetCertificate.from(certificateId),
                reportType,
                dateCond
        );

        return Response.success(ReportResponse.from(reportData));
    }

    @GetMapping("/certificates/{certificateId}/mock-exam-results/{dateType}")
    public Response<PageResult<MockExamResultResponse>> mockExamResults(@PathVariable Long certificateId,
                                                                        @PathVariable DateType dateType,
                                                                        DateCond dateCond,
                                                                        @AuthenticationPrincipal User currentUser,
                                                                        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Cursor cursor) {
        PageResult<MockExamResultResponse> responses = readMockExamResultService.findMockExamResults(
                TargetUser.from(currentUser.getId()),
                TargetCertificate.from(certificateId),
                dateType,
                dateCond,
                cursor)
                .map(MockExamResultResponse::from);
        return Response.success(responses);
    }



}
