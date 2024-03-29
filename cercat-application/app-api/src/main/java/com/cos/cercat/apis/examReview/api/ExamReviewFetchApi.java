package com.cos.cercat.apis.examReview.api;

import com.cos.cercat.apis.examReview.app.usecase.ExamReviewFetchUseCase;
import com.cos.cercat.apis.examReview.dto.response.ExamReviewResponse;
import com.cos.cercat.dto.ExamReviewSearchCond;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "따끈 후기 조회 API")
public class ExamReviewFetchApi {

    private final ExamReviewFetchUseCase examReviewFetchUseCase;

    @GetMapping("/certificates/{certificateId}/exam-reviews")
    @Operation(summary = "최근 시험의 따끈 후기 조회")
    public Response<Slice<ExamReviewResponse>> getExamReviews(@PathVariable Long certificateId,
                                                              ExamReviewSearchCond cond,
                                                              @PageableDefault Pageable pageable) {
        return Response.success(examReviewFetchUseCase.getExamReviews(certificateId, cond, pageable));
    }

    @GetMapping("/certificates/{certificateId}/check-reviews")
    @Operation(summary = "따끈후기 작성 대상자인지 조회")
    public Response<Boolean> checkReviewDateAfterExamDate(@PathVariable Long certificateId,
                                                          @AuthenticationPrincipal UserDTO userDTO) {
        return Response.success(examReviewFetchUseCase.checkReviewDateAfterExamDate(certificateId, userDTO.getId()));
    }

}
