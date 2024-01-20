package com.cos.cercat.learning.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.learning.app.LearningCreateService;
import com.cos.cercat.learning.dto.request.GoalCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
                                     @RequestBody GoalCreateRequest request) {
        learningCreateService.createGoal(request, certificateId, 1L);

        return Response.success("목표 생성 성공");
    }

    @PostMapping("/{certificateId}/goals/study-times")
    @Operation(summary = "공부 시간 생성(누적)")
    public Response<Void> createStudyTimeLog(@PathVariable Long certificateId, Long studyTime) {
        learningCreateService.createStudyTimeLog(certificateId, studyTime, 1L);
        return Response.success("공부 시간 생성(누적) 성공");
    }

}
