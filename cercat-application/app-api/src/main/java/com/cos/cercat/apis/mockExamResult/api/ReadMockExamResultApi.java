package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.common.domain.PageResult;
import com.cos.cercat.domain.mockexamresult.*;
import com.cos.cercat.apis.mockExamResult.dto.response.*;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.TargetMockExam;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.cos.cercat.apis.global.util.CursorConvertor.toCursor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class ReadMockExamResultApi implements ReadMockExamResultApiDocs {

    private final ReadMockExamResultService readMockExamResultService;

    @GetMapping("/mock-exams/{mockExamId}")
    public Response<List<MockExamResultWithSubjectsResponse>> mockExamResultDetails(@PathVariable Long mockExamId,
                                                                                    @AuthenticationPrincipal UserDTO user) {
        List<MockExamResultDetail> mockExamResultDetails = readMockExamResultService.getMockExamResultDetail(TargetMockExam.from(mockExamId), TargetUser.from(user.getId()));
        List<MockExamResultWithSubjectsResponse> responses = mockExamResultDetails.stream()
                .map(MockExamResultWithSubjectsResponse::from)
                .toList();
        return Response.success(responses);
    }

    @GetMapping("/certificates/{certificateId}/user-answers/wrong-answers")
    public Response<SliceResult<UserAnswerResponse>> allWrongUserAnswers(@PageableDefault Pageable pageable,
                                                                         @PathVariable Long certificateId,
                                                                         @AuthenticationPrincipal UserDTO currentUser) {
        SliceResult<UserAnswer> allWrongUserAnswers = readMockExamResultService.getAllWrongUserAnswers(
                TargetCertificate.from(certificateId),
                TargetUser.from(currentUser.getId()),
                toCursor(pageable));

        return Response.success(allWrongUserAnswers.map(UserAnswerResponse::from));
    }

    @GetMapping("/mock-exam-results/{mockExamResultId}/user-answers/wrong-answers")
    public Response<SliceResult<UserAnswerResponse>> wrongUserAnswers(@PageableDefault() Pageable pageable,
                                                                      @PathVariable Long mockExamResultId,
                                                                      @AuthenticationPrincipal UserDTO currentUser) {
        SliceResult<UserAnswerResponse> responses = readMockExamResultService.getWrongUserAnswers(
                TargetMockExamResult.from(mockExamResultId),
                TargetUser.from(currentUser.getId()),
                toCursor(pageable)
        ).map(UserAnswerResponse::from);

        return Response.success(responses);
    }

    @GetMapping("/certificates/{certificateId}/mock-exam-results/average")
    public Response<List<SubjectResultsAVGResponse>> subjectResultsAVG(@PathVariable Long certificateId,
                                                                       @AuthenticationPrincipal UserDTO currentUser) {
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
                                                @AuthenticationPrincipal UserDTO currentUser) {
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
                                                                        @AuthenticationPrincipal UserDTO currentUser,
                                                                        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResult<MockExamResultResponse> responses = readMockExamResultService.findMockExamResults(
                TargetUser.from(currentUser.getId()),
                TargetCertificate.from(certificateId),
                dateType,
                dateCond,
                toCursor(pageable))
                .map(MockExamResultResponse::from);
        return Response.success(responses);
    }



}
