package com.cos.cercat.apis.learning.api;

import com.cos.cercat.apis.learning.app.usecase.LearningUpdateUseCase;
import com.cos.cercat.apis.learning.dto.request.GoalUpdateRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "학습 수정 API")
public class LearningUpdateApi {

    private final LearningUpdateUseCase learningUpdateUseCase;

    @PutMapping("/goals/{goalId}")
    public Response<Void> updateGoal(@PathVariable Long goalId,
                                     @RequestBody GoalUpdateRequest request,
                                     @AuthenticationPrincipal UserDTO currentUser) {

        learningUpdateUseCase.updateGoal(goalId, request, currentUser.getId());
        return Response.success("목표 수정 성공");
    }

}
