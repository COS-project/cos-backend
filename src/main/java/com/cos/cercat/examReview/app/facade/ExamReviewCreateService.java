package com.cos.cercat.examReview.app.facade;

import com.cos.cercat.certificate.app.CertificateExamService;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.examReview.app.ExamReviewService;
import com.cos.cercat.examReview.dto.request.ExamReviewCreateRequest;
import com.cos.cercat.learning.app.GoalService;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamReviewCreateService {

    private final ExamReviewService examReviewService;
    private final UserService userService;
    private final CertificateService certificateService;
    private final CertificateExamService certificateExamService;
    private final GoalService goalService;

    /***
     * 자격증 시험의 따끈 후기를 생성합니다.
     * @param request 따끈후기 생성 정보
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     */
    @Transactional
    public void createExamReview(ExamReviewCreateRequest request, Long certificateId , Long userId) {
        User user = userService.getUser(userId);
        Certificate certificate = certificateService.getCertificate(certificateId);

        Goal goal = goalService.getRecentGoal(user, certificate);
        CertificateExam recentCertificateExam = certificateExamService.getRecentCertificateExam(certificate);

        LocalDate prepareStartDate = goal.getPrepareStartDateTime().toLocalDate();
        LocalDate prepareFinishDate = goal.getPrepareFinishDateTime().toLocalDate();

        Period preparePeriod = Period.between(prepareStartDate, prepareFinishDate);
        log.info("user - {}, certificate - {} 따끈 후기 생성", user.getEmail(), certificate.getCertificateName());
        examReviewService.createExamReview(request.toEntity(user, recentCertificateExam, periodToMonths(preparePeriod)));
    }

    private int periodToMonths(Period preparePeriod) {
        return preparePeriod.getYears() * 12 + preparePeriod.getMonths();
    }

}
