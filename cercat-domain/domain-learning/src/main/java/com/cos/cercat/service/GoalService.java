package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.repository.GoalRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.Goal;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

    public void createGoal(Goal goal) {
        goalRepository.save(goal);
    }

    public Goal getRecentGoal(UserEntity userEntity, CertificateEntity certificateEntity) {
        return goalRepository.findRecentGoalByUserEntityAndCertificateEntity(userEntity, certificateEntity).orElseThrow(() ->
                new CustomException(ErrorCode.GOAL_NOT_FOUND));
    }

    public List<Goal> getAllGoals(CertificateEntity certificateEntity, UserEntity userEntity) {
        return goalRepository.findGoalsByUserEntityAndCertificateEntity(userEntity, certificateEntity);
    }

    public Goal getGoalById(Long goalId) {
        return goalRepository.findById(goalId).orElseThrow(() ->
                new CustomException(ErrorCode.GOAL_NOT_FOUND));
    }

    public Boolean isGoalAlreadyExists(UserEntity userEntity, CertificateEntity certificateEntity) {
        return goalRepository.existsGoalByUserEntityAndCertificateEntity(userEntity, certificateEntity);
    }

    @Builder(builderMethodName = "updateGoalBuilder")
    public void updateGoal(Long goalId,
                           Integer goalScore,
                           LocalDateTime prepareStartDateTime,
                           LocalDateTime prepareFinishDateTime,
                           Integer goalPrepareDays,
                           Integer mockExamsPerDay,
                           Integer goalMockExams,
                           List<Integer> mockExamRepeatDays,
                           Long studyTimePerDay,
                           Long goalStudyTime,
                           List<Integer> studyRepeatDays,
                           UserEntity userEntity) {
        Goal goal = getGoalById(goalId);

        if (goal.isAuthorized(userEntity)) {
            goal.updateGoalInfo(
                    goalScore,
                    prepareStartDateTime,
                    prepareFinishDateTime,
                    goalPrepareDays,
                    mockExamsPerDay,
                    goalMockExams,
                    studyTimePerDay,
                    goalStudyTime,
                    mockExamRepeatDays,
                    studyRepeatDays
            );
        }

    }

}
