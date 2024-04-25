package com.cos.cercat.apis.learning.api;

import com.cos.cercat.apis.learning.dto.request.GoalRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "학습 생성 API")
public interface CreateLearningApiDocs {

    @Operation(summary = "목표 생성")
    Response<Void> createGoal(Long certificateId,
                              GoalRequest request,
                              UserDTO currentUser);

    @Operation(summary = "공부 시간 생성(누적)")
    Response<Void> createStudyTimeLog(Long goalId, Long studyTime);

}
