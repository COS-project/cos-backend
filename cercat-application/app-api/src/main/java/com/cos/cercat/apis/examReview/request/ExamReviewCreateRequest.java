package com.cos.cercat.apis.examReview.request;

import com.cos.cercat.domain.examreview.ExamDifficulty;
import com.cos.cercat.domain.examreview.ExamReviewContent;

public record ExamReviewCreateRequest(
        ExamDifficulty examDifficulty,
        String content
) {

    public ExamReviewContent toContent() {
        return new ExamReviewContent(examDifficulty, content);
    }

}
