package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.certificate.CertificateExam;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExamReviewFinder {

    private final ExamReviewRepository examReviewRepository;

    public SliceResult<ExamReview> find(CertificateExam certificateExam, ExamReviewSearchCond cond, Cursor cursor) {
        return examReviewRepository.find(certificateExam, cond, cursor);
    }

}
