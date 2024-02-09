package com.cos.cercat.learning.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.learning.dto.request.GoalCreateRequest;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LearningCreateService {

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
        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);

        if (goalService.isGoalAlreadyExists(user, certificate)) {
            log.warn("user - {}, certificate - {} 목표가 이미 존재합니다.", user.getEmail(), certificate.getCertificateName());
            throw new CustomException(ErrorCode.GOAL_CREATE_ERROR);
        }

        log.info("user - {}, certificate - {} 목표 생성.", user.getEmail(), certificate.getCertificateName());
        goalService.createGoal(request.toEntity(certificate, user));
    }

    /***
     * 유저의 자격증 시험 목표의 공부시간을 누적합니다.
     * @param certificateId 자격증 ID
     * @param studyTime 누적할 공부시간
     * @param userId 유저 ID
     */
    public void createStudyTimeLog(Long certificateId, Long studyTime, Long userId) {
        User user = userService.getUser(userId);
        Certificate certificate = certificateService.getCertificate(certificateId);
        Goal goal = goalService.getGoal(user, certificate);

        log.info("user - {}, goalId - {}, studyTime - {} 공부 시간 누적", user.getEmail(), goal.getId(), studyTime);
        studyTimeLogService.createStudyTimeLog(goal, studyTime);
    }
}
