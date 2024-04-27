package com.cos.cercat.apis.mockExam.api;

import com.cos.cercat.apis.mockExam.response.MockExamResponse;
import com.cos.cercat.apis.mockExam.response.QuestionResponse;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.mockexam.MockExamService;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/mock-exams/{mockExamId}/questions")
    public Response<List<QuestionResponse>> findQuestions(@PathVariable Long mockExamId) {
        List<QuestionResponse> questionResponses = mockExamService.findQuestions(TargetMockExam.from(mockExamId)).stream()
                .map(QuestionResponse::from)
                .toList();

        return Response.success(questionResponses);
    }

    @Override
    @GetMapping("/certificates/{certificateId}/exam-years")
    public Response<List<Integer>> findExamYears(@PathVariable  Long certificateId) {
        return Response.success(mockExamService.findExamYears(TargetCertificate.from(certificateId)));
    }
}
