package com.cos.cercat.apis.mockExamResult.api;

import com.cos.cercat.apis.mockExamResult.request.CreateMockExamResultRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.mockexam.TargetMockExam;
import com.cos.cercat.mockexamresult.CreateMockExamResultService;
import com.cos.cercat.mockexamresult.TargetMockExamResult;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class CreateMockExamResultApi implements MockExamResultApiDocs{

    private final CreateMockExamResultService createMockExamResultService;

    @PostMapping("/mock-exams/{mockExamId}/mock-exam-results")
    public Response<TargetMockExamResult> createMockExamResult(@PathVariable Long mockExamId,
                                                               @RequestBody CreateMockExamResultRequest request,
                                                               @AuthenticationPrincipal User currentUser) {

        TargetMockExamResult targetMockExamResult = createMockExamResultService.createMockExamResult(
                TargetUser.from(currentUser.getId()),
                TargetMockExam.from(mockExamId),
                request.toNewSubjectResults()
        );
        return Response.success(targetMockExamResult);
    }


}
