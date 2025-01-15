package com.cos.cercat.apis.learning.api;

import com.cos.cercat.apis.learning.request.GoalRequest;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.learning.TargetGoal;
import com.cos.cercat.domain.learning.UpdateLearningService;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class UpdateLearningApi implements UpdateLearningApiDocs {

    private final UpdateLearningService updateLearningService;

    @PutMapping("/goals/{goalId}")
    public Response<Void> updateGoal(@PathVariable Long goalId,
                                     @RequestBody GoalRequest request,
                                     @AuthenticationPrincipal User currentUser) {

        updateLearningService.updateGoal(
                TargetUser.from(currentUser.getId()),
                TargetGoal.from(goalId),
                request.toNewGoal()
                );
        return Response.success("목표 수정 성공");
    }
}
