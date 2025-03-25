package com.cos.cercat.apis.examReview.api;

import com.cos.cercat.apis.examReview.request.ExamReviewCreateRequest;
import com.cos.cercat.apis.examReview.response.ExamReviewResponse;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.apis.global.annotation.CursorDefault;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.examreview.ExamReviewSearchCond;
import com.cos.cercat.domain.examreview.ExamReviewService;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class ExamReviewApi implements ExamReviewApiDocs {

    private final ExamReviewService examReviewService;

    @PostMapping("/certificates/{certificateId}/exam-reviews")
    public Response<Void> createExamReview(@PathVariable Long certificateId,
                                           @RequestBody ExamReviewCreateRequest request,
                                           @AuthenticationPrincipal User currentUser) {
        examReviewService.createExamReview(
                TargetUser.from(currentUser.getId()),
                TargetCertificate.from(certificateId),
                request.toContent()
        );
        return Response.success("따끈 후기 생성 성공");
    }

    @GetMapping("/certificates/{certificateId}/exam-reviews")
    public Response<SliceResult<ExamReviewResponse>> getExamReviews(@PathVariable Long certificateId,
                                                              ExamReviewSearchCond cond,
                                                              @CursorDefault Cursor cursor) {
        SliceResult<ExamReviewResponse> responses = examReviewService.getExamReviews(
                TargetCertificate.from(certificateId),
                cond,
                cursor
        ).map(ExamReviewResponse::from);

        return Response.success(responses);
    }

    @GetMapping("/certificates/{certificateId}/check-reviews")
    public Response<Boolean> checkReviewDateAfterExamDate(@PathVariable Long certificateId,
                                                          @AuthenticationPrincipal User currentUser) {
        boolean isTarget = examReviewService.isReviewTarget(
                TargetUser.from(currentUser.getId()),
                TargetCertificate.from(certificateId)
        );
        
        return Response.success(isTarget);
    }
}
