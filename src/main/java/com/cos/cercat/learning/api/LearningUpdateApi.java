package com.cos.cercat.learning.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.learning.app.LearningUpdateService;
import com.cos.cercat.learning.dto.request.GoalUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "학습 수정 API")
public class LearningUpdateApi {

    private final LearningUpdateService learningUpdateService;

    @PutMapping("/goals/{goalId}")
    public Response<Void> updateGoal(@PathVariable Long goalId,
                                     @RequestBody GoalUpdateRequest request) {

        learningUpdateService.updateGoal(goalId, request, 1L);
        return Response.success("목표 수정 성공");
    }

}
