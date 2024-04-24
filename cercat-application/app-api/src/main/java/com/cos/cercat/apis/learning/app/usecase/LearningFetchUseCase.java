package com.cos.cercat.apis.learning.app.usecase;

import com.cos.cercat.apis.learning.dto.response.GoalAchievementResponse;
import com.cos.cercat.apis.learning.dto.response.GoalDetailResponse;
import com.cos.cercat.apis.learning.dto.response.GoalResponse;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.service.*;
import com.cos.cercat.domain.Goal;
import com.cos.cercat.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@UseCase
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LearningFetchUseCase {

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
    public GoalDetailResponse getGoal(Long goalId, UserDTO currentUser) {
        Goal goal = goalService.getGoalById(goalId);

        log.info("currentUser - {}, goalId - {} 목표 조회", currentUser.getEmail(), goalId);
        return GoalDetailResponse.from(goal);
    }

    /***
     * 유저의 목표 설정 여부를 조회합니다.
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     * @return 목표를 설정했다면 true, 목표를 아직 설정하지않았다면 false를 반환합니다.
     */
    public Boolean existsGoal(Long certificateId, Long userId) {
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        UserEntity userEntity = userService.getUser(userId);

        log.info("userEntity - {}, certificateEntity - {} 목표 설정 여부 조회", userEntity.getEmail(), certificateEntity.getCertificateName());
        return goalService.isGoalAlreadyExists(userEntity, certificateEntity);
    }

    /***
     * 유저의 자격증에 대한 목표를 모두 조회합니다.
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     * @return 유저의 목표 리스트를 반환합니다.
     */
    public List<GoalResponse> getAllGoals(Long certificateId, Long userId) {
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        UserEntity userEntity = userService.getUser(userId);

        log.info("userEntity - {} 목표 리스트 조회", userEntity.getEmail());

        return goalService.getAllGoals(certificateEntity, userEntity).stream()
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
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        UserEntity userEntity = userService.getUser(userId);
        Goal goal = goalService.getRecentGoal(userEntity, certificateEntity);

        int currentMaxScore = mockExamResultService.getCurrentMaxScore(certificateEntity, userEntity, goal.getPrepareStartDateTime());
        long todayTotalStudyTime = studyTimeLogService.getTodayTotalStudyTime(goal);
        Long totalStudyTime = studyTimeLogService.getTotalStudyTime(goal);
        int todayMockExamResults = mockExamResultService.countTodayMockExamResults(certificateEntity, userEntity);
        int totalMockExamResults = mockExamResultService.countTotalMockExamResults(certificateEntity, userEntity, goal.getPrepareStartDateTime());

        log.info("userEntity - {}, goalId - {} 목표 달성도 조회", userEntity.getEmail(), goal.getId());
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
