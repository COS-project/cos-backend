package com.cos.cercat.examReview.api;

import com.cos.cercat.examReview.app.ExamReviewCreateService;
import com.cos.cercat.examReview.dto.request.ExamReviewCreateRequest;
import com.cos.cercat.global.Response;
import com.cos.cercat.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "따끈 후기 생성 API")
public class ExamReviewCreateApi {

    private final ExamReviewCreateService examReviewCreateService;

    @PostMapping("/certificates/{certificateId}/exam-reviews")
    @Operation(summary = "따끈후기 작성")
    public Response<Void> createExamReview(@PathVariable Long certificateId,
                                           @RequestBody ExamReviewCreateRequest request,
                                           @AuthenticationPrincipal UserDTO userDTO) {
        examReviewCreateService.createExamReview(request, certificateId, userDTO.getId());
        return Response.success("따끈 후기 생성 성공");
    }
}
