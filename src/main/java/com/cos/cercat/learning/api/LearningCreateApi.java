package com.cos.cercat.learning.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.learning.app.LearningCreateService;
import com.cos.cercat.learning.dto.request.GoalCreateRequest;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "학습 생성 API")
public class LearningCreateApi {

    private final LearningCreateService learningCreateService;

    @PostMapping("/{certificateId}/goals")
    @Operation(summary = "목표 생성")
    public Response<Void> createGoal(@PathVariable Long certificateId,
                                     @RequestBody GoalCreateRequest request,
                                     @AuthenticationPrincipal UserDTO user) {
        learningCreateService.createGoal(request, certificateId, user.getId());

        return Response.success("목표 생성 성공");
    }

    @PostMapping("/{certificateId}/goals/study-times")
    @Operation(summary = "공부 시간 생성(누적)")
    public Response<Void> createStudyTimeLog(@PathVariable Long certificateId,
                                             Long studyTime,
                                             @AuthenticationPrincipal UserDTO user) {
        learningCreateService.createStudyTimeLog(certificateId, studyTime, user.getId());
        return Response.success("공부 시간 생성(누적) 성공");
    }

}
