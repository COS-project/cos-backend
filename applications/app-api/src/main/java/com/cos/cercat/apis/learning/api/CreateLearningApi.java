package com.cos.cercat.apis.learning.api;

import com.cos.cercat.apis.learning.request.GoalRequest;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.learning.CreateLearningService;
import com.cos.cercat.domain.learning.GoalId;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class CreateLearningApi implements CreateLearningApiDocs {

    private final CreateLearningService createLearningService;

    @PostMapping("/certificates/{certificateId}/goals")
    public Response<Void> createGoal(@PathVariable Long certificateId,
                                     @RequestBody GoalRequest request,
                                     @AuthenticationPrincipal User currentUser) {
        createLearningService.createGoal(
                UserId.from(currentUser.getId()),
                CertificateId.from(certificateId),
                request.toNewGoal()
        );

        return Response.success("목표 생성 성공");
    }

    @PostMapping("/goals/{goalId}/study-times")
    public Response<Void> createStudyTimeLog(@PathVariable Long goalId,
                                             Long studyTime) {
        createLearningService.createStudyTimeLog(GoalId.from(goalId), studyTime);
        return Response.success("공부 시간 생성(누적) 성공");
    }

}
