package com.cos.cercat.learning.app.facade;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.learning.app.GoalService;
import com.cos.cercat.learning.app.StudyTimeLogService;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.learning.dto.response.GoalAchievementResponse;
import com.cos.cercat.learning.dto.response.GoalDetailResponse;
import com.cos.cercat.learning.dto.response.GoalResponse;
import com.cos.cercat.mockExamResult.app.MockExamResultService;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LearningFetchService {

    private final GoalService goalService;
    private final CertificateService certificateService;
    private final UserService userService;
    private final StudyTimeLogService studyTimeLogService;
    private final MockExamResultService mockExamResultService;

    /**
     * 목표를 조회합니다.
     * @param goalId 목표 ID
     * @return 목표 상세 정보를 반환합니다.
     */
    public GoalDetailResponse getGoal(Long goalId, UserDTO user) {
        Goal goal = goalService.getGoalById(goalId);

        log.info("user - {}, goalId - {} 목표 조회", user.getEmail(), goalId);
        return GoalDetailResponse.from(goal);
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
     * 유저의 자격증에 대한 목표를 모두 조회합니다.
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     * @return 유저의 목표 리스트를 반환합니다.
     */
    public List<GoalResponse> getAllGoals(Long certificateId, Long userId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);

        log.info("user - {} 목표 리스트 조회", user.getEmail());

        return goalService.getAllGoals(certificate, user).stream()
                .map(GoalResponse::from)
                .sorted(Comparator.comparing(GoalResponse::prepareStartDateTime).reversed())
                .toList();
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
        Goal goal = goalService.getRecentGoal(user, certificate);

        int currentMaxScore = mockExamResultService.getCurrentMaxScore(certificate, user, goal.getPrepareStartDateTime());
        long todayTotalStudyTime = studyTimeLogService.getTodayTotalStudyTime(goal);
        Long totalStudyTime = studyTimeLogService.getTotalStudyTime(goal);
        int todayMockExamResults = mockExamResultService.countTodayMockExamResults(certificate, user);
        int totalMockExamResults = mockExamResultService.countTotalMockExamResults(certificate, user, goal.getPrepareStartDateTime());

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
