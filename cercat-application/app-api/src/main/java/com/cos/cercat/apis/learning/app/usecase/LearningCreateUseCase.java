package com.cos.cercat.apis.learning.app.usecase;

import com.cos.cercat.apis.learning.dto.request.GoalCreateRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.service.CertificateService;
import com.cos.cercat.service.GoalService;
import com.cos.cercat.service.StudyTimeLogService;
import com.cos.cercat.domain.Goal;
import com.cos.cercat.service.UserService;
import com.cos.cercat.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class LearningCreateUseCase {

    private final GoalService goalService;
    private final CertificateService certificateService;
    private final UserService userService;
    private final StudyTimeLogService studyTimeLogService;

    /***
     * 자격증 시험의 목표를 생성합니다.
     * @param request 목표 생성 정보
     * @param certificateId 자격증 ID
     * @param userId 유저 ID
     */
    public void createGoal(GoalCreateRequest request, Long certificateId, Long userId) {
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        UserEntity userEntity = userService.getUser(userId);

        log.info("userEntity - {}, certificateEntity - {} 목표 생성.", userEntity.getEmail(), certificateEntity.getCertificateName());
        goalService.createGoal(request.toEntity(certificateEntity, userEntity));
    }

    /***
     * 유저가 공부시간을 누적한다.
     * @param goalId 목표 ID
     * @param studyTime 공부 시간
     * @param currentUser 유저 정보
     */
    public void createStudyTimeLog(Long goalId, Long studyTime, UserDTO currentUser) {
        Goal goal = goalService.getGoalById(goalId);

        log.info("userEntity - {}, goalId - {}, studyTime - {} 공부 시간 누적", currentUser.getEmail(), goal.getId(), studyTime);
        studyTimeLogService.createStudyTimeLog(goal, studyTime);
    }
}
