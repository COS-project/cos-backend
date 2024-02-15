package com.cos.cercat.learning.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.learning.dto.request.GoalUpdateRequest;
import com.cos.cercat.learning.repository.GoalRepository;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public void updateGoal(GoalUpdateRequest request,
                           Long goalId,
                           User user) {
        Goal goal = getGoalById(goalId);

        if (goal.isAuthorized(user)) {
            goal.updateGoalInfo(
                    request.goalScore(),
                    request.prepareStartDateTime(),
                    request.prepareFinishDateTime(),
                    request.goalPrepareDays(),
                    request.mockExamsPerDay(),
                    request.goalMockExams(),
                    request.studyTimePerDay(),
                    request.goalStudyTime(),
                    request.mockExamRepeatDays(),
                    request.studyRepeatDays()
            );
        }

    }

}
