package com.cos.cercat.examReview.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.app.ExamInfoService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.ExamInfo;
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
    private final ExamInfoService examInfoService;
    private final GoalService goalService;

    @Transactional
    public void createExamReview(ExamReviewCreateRequest request, Long certificateId ,Long userId) {
        User user = userService.getUser(userId);
        Certificate certificate = certificateService.getCertificate(certificateId);

        Goal goal = goalService.getGoal(user, certificate);
        ExamInfo recentExamInfo = examInfoService.getRecentExamInfo(certificate);

        LocalDate prepareStartDate = goal.getPrepareStartDateTime().toLocalDate();
        LocalDate prepareFinishDate = goal.getPrepareFinishDateTime().toLocalDate();

        Period preparePeriod = Period.between(prepareStartDate, prepareFinishDate);
        examReviewService.createExamReview(request.toEntity(user, recentExamInfo.getCertificateExam(), periodToMonths(preparePeriod)));
    }


    private int periodToMonths(Period preparePeriod) {
        return preparePeriod.getYears() * 12 + preparePeriod.getMonths();
    }

}
