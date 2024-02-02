package com.cos.cercat.learning.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.learning.dto.response.GoalAchievementResponse;
import com.cos.cercat.learning.dto.response.GoalResponse;
import com.cos.cercat.mockExamResult.app.MockExamResultService;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LearningFetchService {

    private final GoalService goalService;
    private final CertificateService certificateService;
    private final UserService userService;
    private final StudyTimeLogService studyTimeLogService;
    private final MockExamResultService mockExamResultService;

    public GoalResponse getGoal(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);
        Goal goal = goalService.getGoal(user, certificate);

        return GoalResponse.from(goal);
    }

    public Boolean existsGoal(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);

        return goalService.isGoalAlreadyExists(user, certificate);
    }

    public GoalAchievementResponse getGoalAchievement(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);
        Goal goal = goalService.getGoal(user, certificate);

        int currentMaxScore = mockExamResultService.getCurrentMaxScore(certificate, user);
        long todayTotalStudyTime = studyTimeLogService.getTodayTotalStudyTime(goal);
        Long totalStudyTime = studyTimeLogService.getTotalStudyTime(goal);
        int todayMockExamResults = mockExamResultService.countTodayMockExamResults(certificate, user);
        int totalMockExamResults = mockExamResultService.countMockExamResults(certificate, user);

        return GoalAchievementResponse.of(
                goal,
                currentMaxScore,
                todayTotalStudyTime,
                totalStudyTime,
                todayMockExamResults,
                totalMockExamResults
        );
    }

}
