package com.cos.cercat.apis.mockExam.api;

import com.cos.cercat.apis.mockExam.request.CreateMockExamRequest;
import com.cos.cercat.apis.mockExam.response.MockExamResponse;
import com.cos.cercat.apis.mockExam.response.QuestionResponse;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.mockexam.MockExamService;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class MockExamApi implements MockExamApiDocs {

    private final MockExamService mockExamService;

    @GetMapping("/certificates/{certificateId}/mock-exams")
    public Response<List<MockExamResponse>> findMockExams(@PathVariable Long certificateId,
                                                          Integer examYear,
                                                          @AuthenticationPrincipal User currentUser) {
        List<MockExamResponse> mockExamResponses = mockExamService.find(TargetCertificate.from(certificateId), examYear).stream()
                .map(MockExamResponse::from)
                .toList();

        return Response.success(mockExamResponses);
    }

    @Override
    @GetMapping("/certificates/{certificateId}/exam-years")
    public Response<List<Integer>> findExamYears(@PathVariable  Long certificateId) {
        return Response.success(mockExamService.findExamYears(TargetCertificate.from(certificateId)));
    }

    @Override
    @GetMapping("/mock-exams/{mockExamId}/questions")
    public Response<List<QuestionResponse>> findQuestions(@PathVariable Long mockExamId) {
        List<QuestionResponse> questionResponses = mockExamService.findQuestions(TargetMockExam.from(mockExamId)).stream()
                .map(QuestionResponse::from)
                .toList();

        return Response.success(questionResponses);
    }

    @Override
    @PostMapping("/mock-exams/{certificateId}")
    public Response<Void> createMockExam(@PathVariable Long certificateId,
                                         @RequestBody CreateMockExamRequest request) {
        mockExamService.createMockExam(
                TargetCertificate.from(certificateId),
                request.toSession(),
                request.timeLimit(),
                request.toContent()
        );

        return Response.success("모의고사 생성 성공");
    }
}
