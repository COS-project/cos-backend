package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.certificate.*;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamReviewService {

    private final UserReader userReader;
    private final CertificateReader certificateReader;
    private final ExamReviewAppender examReviewAppender;
    private final CertificateExamReader certificateExamReader;
    private final InterestCertificateManager interestCertificateManager;
    private final ExamReviewReader examReviewReader;


    public void createExamReview(UserId userId,
                                 CertificateId certificateId,
                                 ExamReviewContent review) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        examReviewAppender.append(user, certificate, review);
    }

    public SliceResult<ExamReview> getExamReviews(
            CertificateId certificateId,
            ExamReviewSearchCond cond,
            Cursor cursor
    ) {
        Certificate certificate = certificateReader.read(certificateId);
        CertificateExam certificateExam = certificateExamReader.readPreviousExam(certificate);
        return examReviewReader.read(certificateExam, cond, cursor);
    }

    public boolean isReviewTarget(UserId userId,
                                  CertificateId certificateId) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        List<Certificate> interesting = interestCertificateManager.findCertificate(user);
        boolean isInterest = interesting.contains(certificate);

        CertificateExam certificateExam = certificateExamReader.readPreviousExam(certificate);
        boolean notReviewed = !examReviewReader.validate(user, certificateExam);

        return isInterest && notReviewed;
    }

}
