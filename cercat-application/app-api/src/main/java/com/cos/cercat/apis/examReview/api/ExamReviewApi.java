package com.cos.cercat.apis.examReview.api;

import com.cos.cercat.apis.examReview.request.ExamReviewCreateRequest;
import com.cos.cercat.apis.examReview.response.ExamReviewResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.examreview.ExamReviewSearchCond;
import com.cos.cercat.domain.examreview.ExamReviewService;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import static com.cos.cercat.apis.global.util.CursorConvertor.toCursor;

@Service
@RequiredArgsConstructor
@RestController("/api/v2")
public class ExamReviewApi implements ExamReviewApiDocs {

    private final ExamReviewService examReviewService;

    @PostMapping("/certificates/{certificateId}/exam-reviews")
    public Response<Void> createExamReview(@PathVariable Long certificateId,
                                           @RequestBody ExamReviewCreateRequest request,
                                           @AuthenticationPrincipal UserDTO currentUser) {
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
                                                              @PageableDefault Pageable pageable) {
        SliceResult<ExamReviewResponse> responses = examReviewService.getExamReviews(
                TargetCertificate.from(certificateId),
                cond,
                toCursor(pageable)
        ).map(ExamReviewResponse::from);

        return Response.success(responses);
    }

    @GetMapping("/certificates/{certificateId}/check-reviews")
    public Response<Boolean> checkReviewDateAfterExamDate(@PathVariable Long certificateId,
                                                          @AuthenticationPrincipal UserDTO currentUser) {
        boolean isTarget = examReviewService.isReviewTarget(
                TargetUser.from(currentUser.getId()),
                TargetCertificate.from(certificateId)
        );
        return Response.success(isTarget);
    }
}
