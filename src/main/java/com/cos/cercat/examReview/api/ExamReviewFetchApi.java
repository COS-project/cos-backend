package com.cos.cercat.examReview.api;

import com.cos.cercat.examReview.app.ExamReviewFetchService;
import com.cos.cercat.examReview.dto.request.ExamReviewSearchCond;
import com.cos.cercat.examReview.dto.response.ExamReviewResponse;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "따끈 후기 조회 API")
public class ExamReviewFetchApi {

    private final ExamReviewFetchService examReviewFetchService;

    @GetMapping("/{certificateId}/exam-reviews")
    public Response<Slice<ExamReviewResponse>> getExamReviews(@PathVariable Long certificateId,
                                                              ExamReviewSearchCond cond,
                                                              @PageableDefault Pageable pageable) {
        return Response.success(examReviewFetchService.getExamReviews(certificateId, cond, pageable));
    }


}
