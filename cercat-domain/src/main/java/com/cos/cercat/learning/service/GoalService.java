package com.cos.cercat.learning.service;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.exception.CustomException;
import com.cos.cercat.exception.ErrorCode;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.learning.repository.GoalRepository;
import com.cos.cercat.user.domain.User;
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

    public Goal getRecentGoal(User user, Certificate certificate) {
        return goalRepository.findRecentGoalByUserAndCertificate(user, certificate).orElseThrow(() ->
                new CustomException(ErrorCode.GOAL_NOT_FOUND));
    }

    public List<Goal> getAllGoals(Certificate certificate, User user) {
        return goalRepository.findGoalsByUserAndCertificate(user, certificate);
    }

    public Goal getGoalById(Long goalId) {
        return goalRepository.findById(goalId).orElseThrow(() ->
                new CustomException(ErrorCode.GOAL_NOT_FOUND));
    }

    public Boolean isGoalAlreadyExists(User user, Certificate certificate) {
        return goalRepository.existsGoalByUserAndCertificate(user, certificate);
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
                           User user) {
        Goal goal = getGoalById(goalId);

        if (goal.isAuthorized(user)) {
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
