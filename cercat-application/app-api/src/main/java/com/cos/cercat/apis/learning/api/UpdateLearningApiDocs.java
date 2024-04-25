package com.cos.cercat.apis.learning.api;

import com.cos.cercat.apis.learning.dto.request.GoalRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "학습 수정 API")
public interface UpdateLearningApiDocs {

    @Operation(summary = "목표 수정")
    Response<Void> updateGoal(Long goalId,
                              GoalRequest request,
                              UserDTO currentUser);

}
