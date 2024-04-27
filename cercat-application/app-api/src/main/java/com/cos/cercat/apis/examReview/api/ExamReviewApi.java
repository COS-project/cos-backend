package com.cos.cercat.apis.examReview.api;

import com.cos.cercat.apis.examReview.request.ExamReviewCreateRequest;
import com.cos.cercat.apis.examReview.response.ExamReviewResponse;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.examreview.ExamReviewSearchCond;
import com.cos.cercat.examreview.ExamReviewService;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RequiredArgsConstructor
@RestController("/api/v2")
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
                                                              Cursor cursor) {
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
