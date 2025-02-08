package com.cos.cercat.domain.examreview;

import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.certificate.*;
import com.cos.cercat.domain.learning.Goal;
import com.cos.cercat.domain.learning.GoalReader;
import com.cos.cercat.domain.user.TargetUser;
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


    public void createExamReview(TargetUser targetUser,
                                 TargetCertificate targetCertificate,
                                 ExamReviewContent review) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        examReviewAppender.append(user, certificate, review);
    }

    public SliceResult<ExamReview> getExamReviews(
            TargetCertificate targetCertificate,
            ExamReviewSearchCond cond,
            Cursor cursor
    ) {
        Certificate certificate = certificateReader.read(targetCertificate);
        CertificateExam certificateExam = certificateExamReader.readPreviousExam(certificate);
        return examReviewReader.read(certificateExam, cond, cursor);
    }

    public boolean isReviewTarget(TargetUser targetUser,
                                  TargetCertificate targetCertificate) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        List<Certificate> interesting = interestCertificateManager.findCertificate(user);
        boolean isInterest = interesting.contains(certificate);

        CertificateExam certificateExam = certificateExamReader.readPreviousExam(certificate);
        boolean notReviewed = !examReviewReader.existReview(user, certificateExam);

        return isInterest && notReviewed;
    }

}
