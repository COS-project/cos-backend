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

    /***
     * 목표를 조회합니다.
     * @param certificateId
     * @param userId
     * @return 목표 Response DTO
     */
    public GoalResponse getGoal(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);
        Goal goal = goalService.getGoal(user, certificate);

        log.info("user - {}, certificate - {} 목표 조회", user.getEmail(), certificate.getCertificateName());
        return GoalResponse.from(goal);
    }

    /***
     * 유저의 목표 설정 여부를 조회합니다.
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     * @return 목표를 설정했다면 true, 목표를 아직 설정하지않았다면 false를 반환합니다.
     */
    public Boolean existsGoal(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);

        log.info("user - {}, certificate - {} 목표 설정 여부 조회", user.getEmail(), certificate.getCertificateName());
        return goalService.isGoalAlreadyExists(user, certificate);
    }

    /***
     * 유저의 목표 달성도를 조회합니다.
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     * @return 목표 달성도 Response DTO를 조회합니다.
     */
    public GoalAchievementResponse getGoalAchievement(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);
        Goal goal = goalService.getGoal(user, certificate);

        int currentMaxScore = mockExamResultService.getCurrentMaxScore(certificate, user);
        long todayTotalStudyTime = studyTimeLogService.getTodayTotalStudyTime(goal);
        Long totalStudyTime = studyTimeLogService.getTotalStudyTime(goal);
        int todayMockExamResults = mockExamResultService.countTodayMockExamResults(certificate, user);
        int totalMockExamResults = mockExamResultService.countMockExamResults(certificate, user);

        log.info("user - {}, goalId - {} 목표 달성도 조회", user.getEmail(), goal.getId());
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
